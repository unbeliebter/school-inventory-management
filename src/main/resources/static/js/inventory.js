console.log("WOW MUCH CONSOLE LOG");

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

async function deleteTableEntry(tableItemId, deleteBtn) {
    if (await notificationDialog.showConfirm("Wirklich löschen?")) {  
        try {
            const response = await fetch(PATH + "/remove?tableItemId=" + tableItemId, {method:"DELETE"});
            if (response.status === 200) {
                let el = deleteBtn;
                while (el && el.parentNode) {
                    el = el.parentNode;
                    if (el.tagName === "TR") {
                        el.remove();
                    }
                }
            }
            else if (response.status === 423) {
                alert("Der zu löschende Eintrag wird noch in einer anderen Tabelle referenziert")
            }
        } catch (error) {
            alert(error.message);
        }
    }
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