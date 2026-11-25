//require supertest
const request = require("supertest");
//url of localserver is assigned to app
const app = process.env.API_URL || "http://localhost:3000";

describe("GET /api/welcome", () => {
    it("welcome message", async () => {
        return request(app)
            .get("/api/welcome")
            .expect('Content-Type', 'application/json; charset=utf-8')
            .expect(200)
            .then((res) => {
                expect(res.body.message).toBe("Welcome to Traffic-Light!");
                expect(res.body.status).toBe("success");
            })
    });
});

describe("GET /api/trafficlight", () => {
    it("out of order", async () => {
        return request(app)
            .get("/api/trafficlight")
            .expect('Content-Type', 'application/json; charset=utf-8')
            //be aware of empty spaces
            .expect(200)
            .then((res) => {
                expect(res.body.message).toBe("OUT OF ORDER");
                expect(res.body.status).toBe("success");
            })
    });
});

describe("GET /api/trafficlight/:color", () => {
    it("red", async () => {
        return request(app)
            .get("/api/trafficlight/red")
            .expect('Content-Type', 'application/json; charset=utf-8')
            .expect(200)
            .then((res) => {
                expect(res.body.message).toBe("DON'T WALK!");
                expect(res.body.status).toBe("success");
            })
    });

    it("green", async () => {
        return request(app)
            .get("/api/trafficlight/green")
            .expect('Content-Type', 'application/json; charset=utf-8')
            .expect(200)
            .then((res) => {
                expect(res.body.message).toBe("WALK!");
                expect(res.body.status).toBe("success");
            })
    });

    it("orange", async () => {
        return request(app)
            .get("/api/trafficlight/orange")
            .expect('Content-Type', 'application/json; charset=utf-8')
            .expect(200)
            .then((res) => {
                expect(res.body.message).toBe("ATTENTION!");
                expect(res.body.status).toBe("success");
            })
    });
});
