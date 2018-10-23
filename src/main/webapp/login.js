

//Login Fuctions
const isManagerLog = (userRole) => {
    if (userRole !== "manager") {
        return false
    }

    $("#loginErrorMsg").html(
        `<div>
                <div>
                    Would You like to log in as: ?
                </div>
                <div>
                <input type = "button" value = "Manager" id = "managerSelect" />
                <input type = "button" value = "Employee" id = "employeeSelect" />
                </div>
            </div>`
    )

    return true;
}

const doLogin = async () => {
    data = {}
    data.username = $("#loginUsername").val().trim();
    data.password = $("#loginPassword").val().trim();
    try {
        let result = await getAjax(`${baseUrl}/login`, post(data))
        state.loggedIn = true;
        state.activeUser = result;
        loginErrorMsg("Login Successful!")
    } catch (error) {
        loginErrorMsg(error.responseText);
    }

    controller(state);
}

const loginErrorMsg = (msg) => {
    $("#loginErrorMsg").text(msg);
}