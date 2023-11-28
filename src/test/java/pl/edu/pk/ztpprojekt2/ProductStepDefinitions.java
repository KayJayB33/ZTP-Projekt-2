package pl.edu.pk.ztpprojekt2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.bson.Document;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import pl.edu.pk.ztpprojekt2.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features")
public class ProductStepDefinitions {
    private static HttpClient httpclient;
    private static ObjectMapper objectMapper;
    private static Logger logger;
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    private String API_BASE_URL;
    private String PRODUCT_ENDPOINT;
    private Product newProduct;
    private HttpResponse<String> response;
    private String productId;

    @BeforeAll
    public static void before_all() {
        logger = LoggerFactory.getLogger(ProductStepDefinitions.class);
        httpclient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Deletes all created documents after each test.
     */
    @After
    public void after() {
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase("ztpprojekt2-integration-tests");
            MongoCollection<Document> products = database.getCollection("products");
            MongoCollection<Document> jv_head_id = database.getCollection("jv_head_id");
            MongoCollection<Document> jv_snapshots = database.getCollection("jv_snapshots");
            try {
                DeleteResult productsResult = products.deleteMany(new Document());
                // Prints the number of deleted documents
                logger.info(() -> "Deleted products count: " + productsResult.getDeletedCount());

                DeleteResult jvHeadIdResult = jv_head_id.deleteMany(new Document());
                // Prints the number of deleted documents
                System.out.println("Deleted jv_head_id count: " + jvHeadIdResult.getDeletedCount());

                DeleteResult jvSnapshotsResult = jv_snapshots.deleteMany(new Document());
                // Prints the number of deleted documents
                System.out.println("Deleted jv_snapshots count: " + jvSnapshotsResult.getDeletedCount());

                // Prints a message if any exceptions occur during the operation
            } catch (MongoException me) {
                System.err.println("Unable to delete due to an error: " + me);
            }
        }
    }

    @Given("the API base URL is {string}")
    public void the_api_base_url_is(String string) {
        // Write code here that turns the phrase above into concrete actions
        API_BASE_URL = string;
    }

    @Given("the product endpoint is {string}")
    public void the_product_endpoint_is(String string) {
        // Write code here that turns the phrase above into concrete actions
        PRODUCT_ENDPOINT = string;
    }

    @When("I add a new product with the following details:")
    public void i_add_a_new_product_with_the_following_details(DataTable dataTable) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        Map<String, String> example = dataTable.asMaps(String.class, String.class).get(0);
        newProduct = new Product();
        newProduct.setName(example.get("name"));
        newProduct.setDescription(example.get("description"));
        newProduct.setPrice(new BigDecimal(example.get("price")));
        newProduct.setAvailableQuantity(Integer.parseInt(example.get("availableQuantity")));

        String newProductJson = objectMapper.writeValueAsString(newProduct);

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + PRODUCT_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newProductJson))
                .build();

        response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        assertThat(response.statusCode()).isEqualTo(int1);
    }

    @And("the response should contain new product id")
    public void the_response_should_contain_new_product_id() {
        // Write code here that turns the phrase above into concrete actions
        assertThat(response.body()).isNotNull().isNotEmpty();
        productId = response.body();
    }

    @And("the product details should be as follows:")
    public void the_product_details_should_be_as_follows(DataTable dataTable) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + PRODUCT_ENDPOINT + "/" + productId))
                .GET()
                .build();

        HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.body()).isNotNull().isNotEmpty();

        ProductResponse productResponse = objectMapper.readValue(response.body(), ProductResponse.class);
        Map<String, String> example = dataTable.asMaps(String.class, String.class).get(0);
        newProduct = new Product();
        newProduct.setName(example.get("name"));
        newProduct.setDescription(example.get("description"));
        newProduct.setPrice(new BigDecimal(example.get("price")));
        newProduct.setAvailableQuantity(Integer.parseInt(example.get("availableQuantity")));

        assertThat(productResponse.name()).isEqualTo(newProduct.getName());
        assertThat(productResponse.description()).isEqualTo(newProduct.getDescription());
        assertThat(productResponse.price()).isEqualTo(newProduct.getPrice());
        assertThat(productResponse.availableQuantity()).isEqualTo(newProduct.getAvailableQuantity());
        assertThat(productResponse.productState()).isEqualTo(newProduct.getProductState());
    }

    @Then("the response message should be {string}")
    public void the_response_message_should_be(String string) {
        assertThat(response.body()).contains(string);
    }

    @Given("a product with the following details exists:")
    public void a_product_with_the_following_details_exists(DataTable dataTable) throws IOException, InterruptedException {
        Map<String, String> example = dataTable.asMaps(String.class, String.class).get(0);
        newProduct = new Product();
        newProduct.setName(example.get("name"));
        newProduct.setDescription(example.get("description"));
        newProduct.setPrice(new BigDecimal(example.get("price")));
        newProduct.setAvailableQuantity(Integer.parseInt(example.get("availableQuantity")));

        String newProductJson = objectMapper.writeValueAsString(newProduct);

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + PRODUCT_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newProductJson))
                .build();

        response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        productId = response.body();
    }

    @When("I update the product with availableQuantity set to {int}")
    public void i_update_the_product_with_available_quantity_set_to(Integer int1) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        newProduct.setAvailableQuantity(int1);
        String newProductJson = objectMapper.writeValueAsString(newProduct);

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + PRODUCT_ENDPOINT + "/" + productId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(newProductJson))
                .build();

        response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @When("I update the product with the following details:")
    public void i_update_the_product_with_the_following_details(DataTable dataTable) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        Map<String, String> example = dataTable.asMaps(String.class, String.class).get(0);
        Product updatedProduct = new Product();
        updatedProduct.setName(example.get("name"));
        updatedProduct.setDescription(example.get("description"));
        updatedProduct.setPrice(new BigDecimal(example.get("price")));
        updatedProduct.setAvailableQuantity(Integer.parseInt(example.get("availableQuantity")));

        String newProductJson = objectMapper.writeValueAsString(updatedProduct);

        HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + PRODUCT_ENDPOINT + "/" + productId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(newProductJson))
                .build();

        response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    @Then("the product history should contain the following entries:")
    public void the_product_history_should_contain_the_following_entries(DataTable dataTable) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + PRODUCT_ENDPOINT + "/" + productId + "/history"))
                .GET()
                .build();

        HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.body()).isNotNull().isNotEmpty();
        ProductResponse[] productResponses = objectMapper.readValue(response.body(), ProductResponse[].class);
        List<Map<String, String>> examples = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < examples.size(); i++) {
            ProductResponse productResponse = productResponses[i];
            assertThat(productResponse.name()).isEqualTo(examples.get(i).get("name"));
            assertThat(productResponse.description()).isEqualTo(examples.get(i).get("description"));
            assertThat(productResponse.price().doubleValue()).isEqualTo(Double.parseDouble(examples.get(i).get("price")));
            assertThat(productResponse.availableQuantity()).isEqualTo(Integer.parseInt(examples.get(i).get("availableQuantity")));
        }
    }
}
