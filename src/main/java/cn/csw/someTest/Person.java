package cn.csw.someTest;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lier
 * @since 2025/6/23 11:23
 */
@Data
@NoArgsConstructor
public class Person {

    private String name;

    private int age;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Map<String,Object> additionalProperties = new HashMap<>();


    @JsonAnySetter
    public void addAdditionalProperty(String key,Object value){
        additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties(){
        return additionalProperties;
    }

    public static void main(String[] args)throws Exception {
        String json = """
                {
                	"name": "John",
                	"age": 30,
                	"address": "123 Street",
                	"nickname": "Johnny"
                }
                """;

        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(json, Person.class);

        System.out.println("Name: " + person.name);  // 输出：Name: John
        System.out.println("Age: " + person.age);    // 输出：Age: 30
        System.out.println("Additional Properties: " + person.getAdditionalProperties());
        // 输出：Additional Properties: {address=123 Street, nickname=Johnny}


        Person person2 = new Person("csw", 31);
        person2.addAdditionalProperty("address", "china guangzhou");
        person2.addAdditionalProperty("nickname", "lier");

        String json2 = mapper.writeValueAsString(person2);
        System.out.println(json2);  // 输出：{"name":"csw","age":31,"address":"china guangzhou","nickname":"lier"}

    }
}
