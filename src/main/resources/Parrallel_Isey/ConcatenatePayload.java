

//  UPDATE JOB Callout :  http://192.168.132.176:54040/api.vixen/jobs/id/01M00095XXX   ID is updated by path param url




        given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"companyNumber\": \"01\",\n" +
                        "    \"jobId\": \""+jobId+"\",\n" +       // if path param url  not use. Concatenate variable
                        "    \"contact\": \"John\",\n" +
                        "    \"description\": \"UPDATED Now New Call Out\",\n" +
                        "    \"siteAccount\": \"ADE004\",\n" +
                        "    \"orderNumber\": \"DODDS-REF4\",\n" +
                        "    \"priority\": \"2\"\n" +
                        "}")
                .when().put("jobs/id/"+jobId+"")

                .then().assertThat().log().all().statusCode(200)
                .contentType("application/json;charset=UTF-8")

                .body("body.jobId", equalTo(jobId)).log().all();