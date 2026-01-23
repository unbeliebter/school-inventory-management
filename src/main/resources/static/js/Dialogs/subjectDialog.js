function fillDialog(subject) {
    let itemId = document.getElementById("id-input");
    let itemNameField = document.getElementById("itemName-input");
    let subjectAbbreviationField = document.getElementById("subject-abbreviation-input");

    itemId.value = subject.id;
    itemNameField.value  = subject.name;
    subjectAbbreviationField.value = subject.abbreviation;

}