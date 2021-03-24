package tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.StudentRegistrationFormPage;
import utils.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Selenide.open;

public class StudentRegistrationFormTest {

    private String firstName, lastName, email, gender, mobileNumber, picture, address, state, city;
    private Calendar dateOfBirth;
    private List<String> subjects, hobbies;
    private Map<String, String> expectedData;

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @BeforeEach
    void generateData() {
        Faker faker = new Faker();

        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        gender = RandomUtils.getRandomGender();
        mobileNumber = faker.number().digits(10);
        dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTime(faker.date().birthday());
        subjects = Arrays.asList("Maths", "Biology", "English");
        hobbies = Arrays.asList("Sports", "Music");
        picture = "picture.png";
        address = faker.address().streetAddress(true);
        state = "Uttar Pradesh";
        city = "Lucknow";

        expectedData = new HashMap<>();
        expectedData.put("Student Name", firstName + " " + lastName);
        expectedData.put("Student Email", email);
        expectedData.put("Gender", gender);
        expectedData.put("Mobile", mobileNumber);
        expectedData.put("Date of Birth", new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH).format(dateOfBirth.getTime()));
        expectedData.put("Subjects", String.join(", ", subjects));
        expectedData.put("Hobbies", String.join(", ", hobbies));
        expectedData.put("Picture", picture);
        expectedData.put("Address", address);
        expectedData.put("State and City", state + " " + city);

    }

    @Test
    void checkStudentRegistrationForm() {
        StudentRegistrationFormPage studentRegistrationFormPage =
                open(StudentRegistrationFormPage.pageUrl, StudentRegistrationFormPage.class);

        studentRegistrationFormPage.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setMobileNumber(mobileNumber)
                .setBirthDate(dateOfBirth)
                .setSubjects(subjects)
                .setHobbies(hobbies)
                .uploadPicture(picture)
                .setCurrentAddress(address)
                .setState(state)
                .setCity(city)
                .submit();

        SoftAssertions softly = new SoftAssertions();
        for (Map.Entry<String, String> data : expectedData.entrySet()) {
            String actualValueInTableRow = studentRegistrationFormPage.getValueInTableRow(data.getKey());
            String expectedValueInTableRow = data.getValue();
            softly.assertThat(actualValueInTableRow).isEqualTo(expectedValueInTableRow);
        }
        softly.assertAll();
    }
}
