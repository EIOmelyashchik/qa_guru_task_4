package pages;

import com.codeborne.selenide.SelenideElement;

import java.util.Calendar;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class StudentRegistrationFormPage {
    public static final String pageUrl = "https://demoqa.com/automation-practice-form";

    private final SelenideElement firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            genderOption = $("#genterWrapper"),
            mobileNumberInput = $("#userNumber"),
            dateOfBirthInput = $("#dateOfBirthInput"),
            monthOfBirthOption = $(".react-datepicker__month-select"),
            yearOfBirthOption = $(".react-datepicker__year-select"),
            subjectsInput = $("#subjectsInput"),
            hobbiesCheckboxes = $("#hobbiesWrapper"),
            uploadPictureButton = $("#uploadPicture"),
            currentAddressTextarea = $("#currentAddress"),
            stateOption = $("#state"),
            cityOption = $("#city"),
            submitButton = $("#submit");

    private final String dayCalendarXPathPattern = "//div[contains(@class, 'datepicker__day') and (text()='%d') and " +
            "not (contains(@class, 'day--outside-month'))]";
    private final String tableRowXPathPattern = "//td[text()='%s']/following-sibling::td";

    public StudentRegistrationFormPage setFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    public StudentRegistrationFormPage setLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    public StudentRegistrationFormPage setEmail(String value) {
        emailInput.setValue(value);
        return this;
    }

    public StudentRegistrationFormPage setGender(String value) {
        genderOption.find(byText(value)).click();
        return this;
    }

    public StudentRegistrationFormPage setMobileNumber(String value) {
        mobileNumberInput.setValue(value);
        return this;
    }

    public StudentRegistrationFormPage setBirthDate(Calendar date) {
        dateOfBirthInput.click();
        monthOfBirthOption.selectOptionByValue(String.valueOf(date.get(Calendar.MONTH)));
        yearOfBirthOption.selectOptionByValue(String.valueOf(date.get(Calendar.YEAR)));
        $x(String.format(dayCalendarXPathPattern, date.get(Calendar.DAY_OF_MONTH))).click();
        return this;
    }

    public StudentRegistrationFormPage setSubjects(List<String> values) {
        values.forEach(value -> subjectsInput.setValue(value).pressEnter());
        return this;
    }

    public StudentRegistrationFormPage setHobbies(List<String> values) {
        values.forEach(value -> hobbiesCheckboxes.find(byText(value)).click());
        return this;
    }

    public StudentRegistrationFormPage uploadPicture(String path) {
        uploadPictureButton.uploadFromClasspath(path);
        return this;
    }

    public StudentRegistrationFormPage setCurrentAddress(String value) {
        currentAddressTextarea.setValue(value);
        return this;
    }

    public StudentRegistrationFormPage setState(String value) {
        stateOption.click();
        stateOption.$("div[class*='menu']").find(byText(value)).click();
        return this;
    }

    public StudentRegistrationFormPage setCity(String value) {
        cityOption.click();
        cityOption.$("div[class*='menu']").find(byText(value)).click();
        return this;
    }

    public void submit() {
        submitButton.click();
    }

    public String getValueInTableRow(String row) {
        return $x(String.format(tableRowXPathPattern, row)).text();
    }

}