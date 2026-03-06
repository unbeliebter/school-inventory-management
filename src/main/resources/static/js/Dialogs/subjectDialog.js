function fillDialog(tableItem) {
    let itemId = document.getElementById("id-input");
    let itemNameField = document.getElementById("itemName-input");
    let subjectAbbreviationField = document.getElementById("subject-abbreviation-input");

    itemId.value = tableItem.id;
    itemNameField.value  = tableItem.name;
    subjectAbbreviationField.value = tableItem.abbreviation;

}

function setFilterOptions() {
    let selectList = document.querySelectorAll("#filterDialog select");
    for (let select of selectList) {
        let filterName = select.getAttribute("name");
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