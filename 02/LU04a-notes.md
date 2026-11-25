# LU04a - Pipeline syntax (notes)

Source: https://wiki.bzz.ch/en/modul/m324_aws/topics/05

## Which syntax

Three ways to define a Jenkins pipeline: **declarative**, a Groovy-based DSL, and XML
(clicked together in the web UI). This course uses the **declarative** syntax, which is
also what Jenkins recommends for new projects. It deliberately offers only a small set of
keywords, so that a pipeline can be read by people who do not write code daily.

Everything is wrapped in a top-level `pipeline { ... }` block.

## The three building blocks

The keywords fall into three groups. Knowing which group a keyword belongs to is the
point of this learning unit, because it explains *where* in the pipeline it may appear.

### 1. Sections — the structure

| Section | Purpose |
| --- | --- |
| `agent` | *Where* the build runs. `any` = any available executor; `label` picks a matching agent; `docker` spins up a container as the build environment. |
| `stages` | Container for one or more `stage` directives. |
| `steps` | Container for the actual instructions inside a stage. |
| `post` | Steps that run *at the end* of the build, guarded by a condition (`always`, `success`, `failure`). Typically notifications. |

### 2. Directives — the configuration

| Directive | Purpose |
| --- | --- |
| `triggers` | How the build starts automatically, e.g. `cron('H/15 * * * *')`. |
| `options` | Pipeline options such as `timeout` (max runtime) or `retry` (reruns after failure). |
| `environment` | Key/value pairs exported as environment variables. Can sit at pipeline *or* stage level. |
| `parameters` | User input asked for before the build starts (e.g. `booleanParam`). |
| `stage` | Logical grouping of steps; gets its own column in the Stage View. |
| `when` | Condition deciding whether a stage runs at all. |
| `tools` | Tools to install and put on `PATH` (this is how Node.js gets into the build — needed in LU04c). |
| `input` | Prompts for input during the run. |
| `parallel` | Stages that run at the same time. |
| `matrix` | Runs stages for each combination of given parameters, in parallel. |

### 3. Steps — the work

| Step | Purpose |
| --- | --- |
| `sh` | Runs a shell command. Almost anything can be expressed with it. |
| custom steps | Convenience wrappers Jenkins or plugins provide, e.g. `echo`, `git`, `checkout`. |
| `script` | Escape hatch: a block of real Groovy, for flow control that declarative syntax can't express (loops, complex conditions). |

## Annotated example from the article

```groovy
pipeline {
     agent any                                   // section: run anywhere
     triggers { cron('H/15 * * * *') }           // directive: every ~15 min
     options { timeout(time: 5) }                // directive: abort after 5 min
     parameters {                                // directive: ask before starting
          booleanParam(name: 'DEBUG_BUILD', defaultValue: true,
                       description: 'Is it the debug build?')
     }
     stages {                                    // section
          stage('Example') {                     // directive
               environment { NAME = 'Node-Backend' }   // directive, stage-scoped
               when { expression { return params.DEBUG_BUILD } }  // directive
               steps {                           // section
                    echo "Building $NAME"        // step
                    script {                     // step: Groovy escape hatch
                         def browsers = ['chrome', 'firefox']
                         for (int i = 0; i < browsers.size(); ++i) {
                              echo "Testing the ${browsers[i]} browser."
                         }
                    }
               }
          }
     }
     post { always { echo 'I will always say Hello again!' } }   // section
}
```

## Things worth remembering

- `agent` is mandatory at the top level. `agent any` is what the LU03 exercises used.
- A `for` loop or an `if` cannot be written directly in `steps` — it needs a `script`
  block. That is the main practical limit of the declarative syntax.
- `post { always ... }` runs even when the build fails, which is what makes it the right
  place for notifications and cleanup, unlike a final stage (which gets skipped on failure).
- `H` in a cron expression means "hash" — Jenkins spreads the load instead of firing every
  job at the exact same minute.
- `tools` is the directive that matters for the next unit: it is how a Node.js installation
  configured in *Manage Jenkins → Tools* is put on the `PATH` of the build.
