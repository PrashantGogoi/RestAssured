package com.epam;

import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Module2_VerifyData
{

    //START -> Verify counts of each resource
    @Test
    public void verifyPostsCount()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
        when().
                get("/posts").
        then().
                body("id.size()",equalTo(100));
    }

    @Test
    public void verifyCommentsCount()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/comments").
                then().
                body("id.size()",equalTo(500));
    }

    @Test
    public void verifyAlbumsCount()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/albums").
                then().
                body("id.size()",equalTo(100));
    }

    @Test
    public void verifyPhotosCount()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/photos").
                then().
                body("id.size()",equalTo(5000));
    }

    @Test
    public void verifyToDosCount()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/todos").
                then().
                body("id.size()",equalTo(200));
    }

    @Test
    public void verifyUsersCount()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/users").
                then().
                body("id.size()",equalTo(10));
    }
    //END -> Verify counts of all resources

    //START -> Verify body
    @Test
    public void verifyPosts()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com/").
        when().
                get("posts/2").
        then().
                assertThat().statusCode(200).
                body("userId", equalTo(1)).
                body("id", equalTo(2)).
                body("title", equalTo("qui est esse")).
                body("body", equalTo("est rerum tempore vitae\n" +
                        "sequi sint nihil reprehenderit dolor beatae ea dolores neque\n" +
                        "fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\n" +
                        "qui aperiam non debitis possimus qui neque nisi nulla"));
    }

    @Test
    public void verifyComments()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com/").
                when().
                get("comments/2").
                then().
                assertThat().statusCode(200).
                body("postId", equalTo(1)).
                body("id", equalTo(2)).
                body("name", equalTo("quo vero reiciendis velit similique earum")).
                body("email", equalTo("Jayne_Kuhic@sydney.com")).
                body("body", equalTo("est natus enim nihil est dolore omnis voluptatem numquam\n" +
                        "et omnis occaecati quod ullam at\n" +
                        "voluptatem error expedita pariatur\n" +
                        "nihil sint nostrum voluptatem reiciendis et"));
    }

    @Test
    public void verifyAlbums()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com/").
                when().
                    get("albums/2").
                then().
                    assertThat().statusCode(200).
                    body("userId", equalTo(1)).
                    body("id", equalTo(2)).
                    body("title", equalTo("sunt qui excepturi placeat culpa"));

    }

    @Test
    public void verifyPhotos()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com/").
                when().
                get("photos/2").
                then().
                assertThat().statusCode(200).
                body("albumId", equalTo(1)).
                body("id", equalTo(2)).
                body("url", equalTo("https://via.placeholder.com/600/771796")).
                body("thumbnailUrl", equalTo("https://via.placeholder.com/150/771796"));
    }

    @Test
    public void verifyToDos()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com/").
                when().
                get("todos/2").
                then().
                assertThat().statusCode(200).
                body("userId", equalTo(1)).
                body("id", equalTo(2)).
                body("title", equalTo("quis ut nam facilis et officia qui")).
                body("completed", equalTo(false));
    }

    @Test
    public void verifyUsers()
    {
        given().
                baseUri("https://jsonplaceholder.typicode.com/").
                when().
                get("users/2").
                then().
                assertThat().statusCode(200).
                body("id", equalTo(2)).
                body("name", equalTo("Ervin Howell")).
                body("username", equalTo("Antonette")).
                body("email", equalTo("Shanna@melissa.tv")).
                body("address.street", equalTo("Victor Plains")).
                body("address.suite", equalTo("Suite 879")).
                body("address.city", equalTo("Wisokyburgh")).
                body("address.zipcode", equalTo("90566-7771")).
                body("address.geo.lat", equalTo("-43.9509")).
                body("address.geo.lng", equalTo("-34.4618")).
                body("phone", equalTo("010-692-6593 x09125")).
                body("website", equalTo("anastasia.net")).
                body("company.name", equalTo("Deckow-Crist")).
                body("company.catchPhrase", equalTo("Proactive didactic contingency")).
                body("company.bs", equalTo("synergize scalable supply-chains"));

    }
    //END -> Verify body

    //START -> Add a user

}
