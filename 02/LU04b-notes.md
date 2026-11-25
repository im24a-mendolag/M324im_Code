# LU04b - Commit pipeline and Jenkins file (notes)

Source: https://wiki.bzz.ch/en/modul/m324_aws/topics/06

## What a commit pipeline is

The **commit pipeline** is the most basic continuous integration process and the starting
point of the whole continuous delivery chain. It is triggered by a commit (a *push* in
Git) to the main repository and ends with a report saying the build succeeded or failed.

Because it runs after *every* change, it has a hard budget:

- it should take **no more than ~5 minutes**, and
- it should use a reasonable amount of resources.

That time budget is the reason it only contains cheap, fast checks. Its value is the
feedback cycle: constant information on whether the code is in a healthy state.

## The flow

1. A developer checks code into the repository.
2. The CI server (Jenkins) detects the change.
3. The build starts.
4. A success/failure report comes back.

## The three classic stages

| Stage | What it does |
| --- | --- |
| **Checkout** | Downloads the source code from the repository. |
| **Compile** | Compiles the source code. |
| **Unit test** | Runs the suite of unit tests. |

Important for our project: the **Compile stage disappears for an interpreted language**.
We use Node.js, so there is nothing to compile — the pipeline becomes Checkout → Install
dependencies → Test. That is exactly the shape the LU04c exercises build up.

## The Jenkins file

Until now all pipeline code was typed directly into Jenkins. The alternative is to put the
pipeline definition into a file named **`Jenkinsfile`** and commit it to the repository
next to the source code.

The article's argument for this is that the pipeline is strictly related to the project
itself: which stages exist and which tools are used depend on the language and the
environment (Node.js here, PyBuilder for a Python project). So the pipeline should be
written by the same people who write the code — the developers — and should live with the
code.

### Benefits

- If Jenkins dies, the pipeline definition is **not lost** — it lives in the repository.
- The **history** of pipeline changes is kept (it is just Git history).
- Pipeline changes go through the **normal development process**, e.g. code review.
- **Access** to pipeline changes is restricted exactly like access to the source code.

### The one structural difference

In a Jenkinsfile the **checkout stage becomes redundant**. Jenkins has to check out the
code *first* in order to read the Jenkinsfile at all, so by the time the pipeline runs,
the sources are already in the workspace.

This is also why Jenkins still needs to know the **repository address** before it can read
the Jenkinsfile — that is configured in the job ("Pipeline script from SCM"), not in the
file itself.

## How this maps to the exercises

| Exercise | Stage of the idea |
| --- | --- |
| 02 | Make the tool (Node.js) available to Jenkins. |
| 03 | Checkout stage — pipeline pulls the code from a Git repo. |
| 04 | Install + test stages — the commit pipeline is complete. |
| 05 | Move that pipeline into `Jenkinsfile1` in the repo and run it via SCM. |
