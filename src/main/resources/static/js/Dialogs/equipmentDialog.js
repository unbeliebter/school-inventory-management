
renterMap = new Map(Object.entries(renterMap));
inventoryNumberSet = new Set();
tableItems.forEach(i => inventoryNumberSet.add(i.inventoryNumber));
let inventoryNumberError = document.getElementById("add-Item-Inventory-Number-Error");

if (openDialogButton !== null) {
    openDialogButton.addEventListener("click", () => {
        resetErrors();
        dialogAddEquipmentStateChanged();
    });
}



function dialogAddEquipmentStateChanged() {
    let equipmentStateField = document.getElementById("equipmentState-input");
    let positionInputDiv = document.getElementById("dialog-add-item-position");
    let renterInputDiv = document.getElementById("dialog-add-item-renter");
    if (equipmentStateField.value === 'ON_LOAN') {
        positionInputDiv.style.display = 'none';
        renterInputDiv.style.display = 'flex';
    } else {
        positionInputDiv.style.display = 'flex';
        renterInputDiv.style.display = 'none';
    }
}

function fillDialog(tableItem) {
    let equipmentId = document.getElementById("id-input");
    let equipmentNameField = document.getElementById("itemName-input");
    let inventoryNumberField = document.getElementById("inventoryNumber-input");
    let equipmentStateField = document.getElementById("equipmentState-input");
    let organizationalUnitField = document.getElementById("organizationalUnit-input");
    let organizationalGroupField = document.getElementById("organizationalGroup-input");
    let subjectField = document.getElementById("subject-input");
    let positionField = document.getElementById("position-input");
    let renterField = document.getElementById("dialog-add-item-renter-input");
    let responsibleUserField = document.getElementById("responsibleUser-input");

    equipmentId.value = tableItem.id;
    equipmentNameField.value  = tableItem.equipmentName;
    inventoryNumberField.value = tableItem.inventoryNumber;
    equipmentStateField.value = tableItem.equipmentState;
    dialogAddEquipmentStateChanged();

    organizationalUnitField.value = tableItem.organizationalUnit.id;
    organizationalGroupField.value = tableItem.organizationalGroup.id;
    subjectField.value = tableItem.subject.id;
    positionField.value = tableItem.position == null ? "" : tableItem.position.id;

    let renter = renterMap.get(tableItem.id);
    if (renter) {
        renterField.value = renter.renter;
    }

    if (responsibleUserField !== null) {
        responsibleUserField.value = tableItem.responsibleUser == null ?  "" : tableItem.responsibleUser.id;
    }
}

function validateOnAdd() {
    resetErrors();
    let form = document.getElementById("newItemForm");

    let inventoryNumberField = document.getElementById("inventoryNumber-input");
    if (inventoryNumberSet.has(inventoryNumberField.value)) {
        inventoryNumberError.setAttribute("shown", "true");
        inventoryNumberError.textContent = "Inventar Nummer existiert bereits!";
        return;
    }

    let equipmentStateField = document.getElementById("equipmentState-input");
    let positionField = document.getElementById("position-input");
    let renterField = document.getElementById("dialog-add-item-renter-input");

    if (equipmentStateField === null || positionField === null) {
        return;
    }

    if (equipmentStateField.value === 'ON_LOAN') {
        positionField.value = "";
        renterField.setAttribute("required", "required");
        positionField.removeAttribute("required");
    } else {
        renterField.value = "";
        positionField.setAttribute("required", "required");
        renterField.removeAttribute("required");
    }

    if (!form.reportValidity()) {
        return;
    }
    form.submit();
}

function resetErrors() {
    inventoryNumberError.setAttribute("shown", "false");
}