function fillDialog(tableItem) {
    let itemId = document.getElementById("id-input");
    let userNameField = document.getElementById("username-input");
    let firstNameField = document.getElementById("firstname-input");
    let lastNameField = document.getElementById("lastname-input");
    let eMailField = document.getElementById("email-input");
    let userTypeField = document.getElementById("userType-input");

    itemId.value = tableItem.id;
    userNameField.value  = tableItem.username;
    firstNameField.value = tableItem.firstname;
    lastNameField.value  = tableItem.lastname;
    eMailField.value  = tableItem.email;
    userTypeField.value  = tableItem.role.frontendName;
}
function setFilterOptions() {
    let selectList = document.querySelectorAll("#filterDialog select");
    for (let select of selectList) {
        let filterName = select.getAttribute("name");
        if (filterName === "frontendName" || filterName === "pwResetRequested") {
            continue;
        }


        let set = new Set();
        allItems.forEach(i => set.add(i[filterName]));

        set.forEach(i => {
            let option = document.createElement("option");
            option.value = i;
            option.text = i;
            select.appendChild(option);
        })
        let options = Array.from(select.childNodes);
        options
            .sort((i1,i2) => i1.value > i2.value)
            .forEach(i => select.appendChild(i));
    }
}
setFilterOptions();