console.log("WOW MUCH CONSOLE LOG")
const dialog = document.getElementById("itemDialog");
const openDialogButton = document.getElementById("openEquipmentDialogButton");
const closeDialogButton = document.getElementById("item-dialog-cancel-button");

const filterDialog = document.getElementById("filterDialog");
const openFilterDialogButton = document.getElementById("openFilterDialogButton");
const closeFilterDialogButton = document.getElementById("filter-dialog-cancel-button");

let tableMap = new Map();
tableItems.forEach((i) => tableMap.set(i.id, i));

if (openDialogButton !== null) {
    openDialogButton.addEventListener("click", () => {
        document.getElementById("id-input").value = "new";
        let errorLabel = document.getElementById("add-Item-Error");
        if (errorLabel !== null) {
            errorLabel.setAttribute("shown", "false");
        }

        dialog.showModal();
    });
}

closeDialogButton.addEventListener("click", () => {
    dialog.close();
});


if (filterDialog !== null) {
    openFilterDialogButton.addEventListener("click", () => filterDialog.showModal());
    closeFilterDialogButton.addEventListener("click", () => filterDialog.close());
}


function editTableItem(itemId) {
    let itemToEdit = tableMap.get(itemId);
    fillDialog(itemToEdit);
    dialog.showModal();
}

function toggleMenu() {
    let navSidebar = document.getElementById("nav-sidebar");
    let isOpen = navSidebar.getAttribute("open");
    if (isOpen === "true") {
        navSidebar.setAttribute("open", "false");
    } else if (isOpen) {
        navSidebar.setAttribute("open", "true");
    }
}

function validateOnAdd() {
    let form = document.getElementById("newItemForm");
    let errorLabel = document.getElementById("add-Item-Error");
    if (!form.reportValidity()) {
        return;
    }

    let equipmentStateField = document.getElementById("equipmentState-input");
    let positionField = document.getElementById("position-input");
    if (equipmentStateField === null || positionField === null) {
        return;
    }

    if (equipmentStateField.value === 'ON_LOAN' && positionField.value !== "") {
        errorLabel.textContent = "Ausgeliehene Objekte dürfen keinen Ort angegeben haben!"
        errorLabel.setAttribute("shown", "true");
        return;
    } else if (equipmentStateField.value !== 'ON_LOAN' && positionField.value === "") {
        errorLabel.textContent = "Bitte einen Lagerort wählen"
        errorLabel.setAttribute("shown", "true");
        return;
    }

    form.submit();
}