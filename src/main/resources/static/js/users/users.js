const STATUS_CREATED = 201;
const STATUS_OK = 200;
const STATUS_CLIENT_ERROR = 400;
let addItemErrorLabel = document.getElementById("add-Item-Error");

tableItemMap = new Map();
tableItems.forEach(i => tableItemMap.set(i.id, i));

userNameMap = new Map();
tableItems.forEach(i => userNameMap.set(i.username, i));

if (openDialogButton !== null) {
    openDialogButton.addEventListener("click", () => {
        resetErrors();
    });
}
function resetErrors() {
    addItemErrorLabel.setAttribute("shown", "false");
}

async function deleteTableEntry_CST(tableItemId, deleteBtn) {
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
                userNameMap.delete(tableItemMap.get(tableItemId).username);
            }
            else if (response.status === 423) {
                await notificationDialog.showError("Der zu löschende Eintrag wird noch in einer anderen Tabelle referenziert")
            }
        } catch (error) {
            await notificationDialog.showError(error.message);
        }
    }
}

async function removePwResetFlag(userId, htmlElement) {
    if (await notificationDialog.showConfirm("Markierung entfernen?")) {
        try {
            const response = await fetch("/users/removePwResetFlag?userId=" + userId, {method:"PATCH"});
            if (response.status === STATUS_OK) {
                let bellOffButton = htmlElement;
                while (htmlElement && htmlElement.parentNode) {
                    htmlElement = htmlElement.parentNode;
                    if (htmlElement.tagName === "TR") {
                        htmlElement.setAttribute("pw-reset-requested", "false");
                    }
                }
                bellOffButton.parentNode.removeChild(bellOffButton);
            }
        } catch (error) {
            alert(error.message);
        }
    }
}

async function resetPasswordOfSelectedUser(userId, username, htmlElement) {
    if (await notificationDialog.showConfirm("Passwort von Nutzer mit Nutzernamen '" + username + "' zurücksetzen?")) {
        try {
            const response = await fetch("/users/resetPassword?userId=" + userId, {method:"POST"});
            if (response.status === STATUS_OK) {
                let oneTimePw = await response.text();
                await notificationDialog.showInitialPassword("Neues einmal passwort: " + oneTimePw, oneTimePw);
                while (htmlElement && htmlElement.parentNode) {
                        htmlElement = htmlElement.parentNode;
                        if (htmlElement.tagName === "TR") {
                            htmlElement.setAttribute("pw-reset-requested", "false");
                        }
                    }
            }
        } catch (error) {
            alert(error.message);
        }
    }
}

async function createUser() {
    let itemId = document.getElementById("id-input").value;
    let roleId = document.getElementById("userType-input").value;
    let newUser = gatherValues()

    if (userNameMap.has(newUser.username) && itemId === "new"
        || (itemId !== "new" && userNameMap.get(newUser.username).id !== itemId)) {
        addItemErrorLabel.textContent = "Nutzername existiert bereits";
        addItemErrorLabel.setAttribute("shown","true");
        return;
    }

    let requestString = "tableItemId=" + itemId
        + "&username=" + newUser.username 
        + "&firstname=" + newUser.firstname 
        + "&lastname=" + newUser.lastname 
        + "&email=" + newUser.email
        + "&roleId=" + roleId;
    try {
        const response = await fetch("/users/addWithRole?" + requestString, {method:"POST"});
        if (response.status === STATUS_OK) {
            location.reload();
            return;
        }
        if (response.status === STATUS_CREATED) {
            let initPw = await response.text();
            await notificationDialog.showInitialPassword("Initial password: " + initPw, initPw);
            location.reload();
        }
    } catch (error) {
        alert(error.message);
    }

    document.getElementById("itemDialog").close();
}

function gatherValues() {
    let itemId = document.getElementById("id-input");
    let userNameField = document.getElementById("username-input");
    let firstNameField = document.getElementById("firstname-input");
    let lastNameField = document.getElementById("lastname-input");
    let eMailField = document.getElementById("email-input");
    let userTypeField = document.getElementById("userType-input");
    return new User(
        itemId.value, 
        userNameField.value, 
        firstNameField.value, 
        lastNameField.value, 
        eMailField.value,
        userTypeField.value,
        false,
        false);
}

function User(itemId, userName, firstName, lastName, email, role) {
    this.id = itemId;
    this.username = userName;
    this.firstname = firstName;
    this.lastname = lastName;
    this.email = email;
    this.role = role;
    // this.changedPassword = changedPassword;
    // this.requiresPasswordReset = requiresPasswordReset;
}