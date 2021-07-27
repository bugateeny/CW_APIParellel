  given()
                .when()
                .get("jobs/reactive/id/"+jobId+"")
                .then()
                .assertThat().statusCode(200)
                .contentType("application/hal+json;charset=UTF-8")
                .body("jobId", equalTo(jobId))
                .log().all();


        //  UPdate
        given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"companyNumber\": \"01\",\n" +
                        "    \"contact\": \"John\",\n" +
                        "    \"description\": \"UPDATED 12 New Call Out\",\n" +
                        "    \"orderNumber\": \"DODDS-REF2\",\n" +
                        "    \"priority\": \"2\"\n" +
                        "}")
                .when().put("jobs/id/"+jobId+"")

                .then().assertThat().statusCode(200)
                .contentType("application/json;charset=UTF-8")

                .body("body.jobId", equalTo(jobId)).log().all();


    }