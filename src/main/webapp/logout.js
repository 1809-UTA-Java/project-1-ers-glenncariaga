const logout = () => {
    state = {
        loggedin: '',
        activeRole: '',
        activeUser: '',
        tableView: "all"
    }
    hideAll();
    $("#login").removeAttr("hidden")
    $("#login").html(`
            <div>
                <label for="loginUsername">Username<input id="loginUsername" name="loginUserName" type="text" /></label>
            </div>
            <div>
                <label for="loginPassword"> Password</label><input id="loginPassword" name="loginPassword" type="text"></input>
            </div>
            <div>
                <input type=button name="loginButton" value="Login" />
            </div>
            <div id="loginErrorMsg"></div>
        `)
}