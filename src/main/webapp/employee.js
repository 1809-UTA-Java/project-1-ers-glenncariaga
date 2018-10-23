//employee Screen
const selectEmployee = () => {
    employeeScreen();
}

const employeeScreen = async () => {

    try {
        hideAll();
        $("#employeeMenu").removeAttr("hidden");
        let list = await getAjax(`${baseUrl}/reimbyuser/${state.activeUser.id}`, get)

        let table = await employeeTable(list);
        $("#employeeMenu").html(employeeNav() + employeeForm() + table);
    } catch (error) {
        console.log(error)
    }
}

const employeeNav = () => {
    return (`
        <div>
            Employee Menu
        </div>
        <div>
            <input type="button" value="Edit Info" id="employeeEdit" />
            <input type="button" value="Reimbursements" id="employeeReim" />
            <input type="button" value="Logout" id="employeeLogout" />
        </div>
                `)
}

const employeeForm = () => {
    return (`
    <div>
        <div>
            ReimbursementForm
        </div>
        <div>
            <label for="reimAmount">Amount</label>
            <input name="reimAmount" type="text" id="reimAmount" />
        </div>
        <div>
            <label for="reimDescription">Description</label>
            <input name="reimDescription" type="text" id="reimDescription" />
        </div>
        <div>
            <label for="reimType">Type</label>
            <input name="reimType" type="text" id="reimType" />
        </div>
        <div>
            <input id="submitReimbursement" type="button" value="Submit" />
        </div>
    </div>
                `)
}

const employeeInfoForm = (user) => {
    return (`
        <div>
                    Edit Employee Information
        </div >
            <div>
                <label for="empInfoFirstName">First Name </label>
                <input value="${user.firstName}" type="text" name"empInfoFirstName" id="empInfoFirstName"/>
            </div>
            <div>
                <label for="empInfoLastName">Last Name </label>
                <input value="${user.lastName}" type="text" name"empInfoLastName" id="empInfoLastName"/>
            </div>
            <div>
                <label for="empInfoEmail">Email</label>
                <input value="${user.email}" type="text" name"empInfoEmail" id="empInfoEmail"/>
            </div>
            <div>
                <label for="empInfoPassword">Password</label>
                <input value="${user.password}" type="text" name"empInfoPassword" id="empInfoPassword"/>
            </div>
            <div>
                <input value="Submit" type="button" id="empInfoSubmit" />
            </div>
                <div id="empInfoMsg"></div>
    `)

}

const employeeTable = async (list) => {
    let tableRows = [];
    if (list != null && list.length > 0) {
        tableRows = list.map(async (rec) => {
            let reciept = null;
            try {
                let config = {
                    type: "GET",
                    contentType: "picture/png",
                    processData: false,
                }
                reciept = await getAjax(`${baseUrl}/file/${rec.id}`, config)
                if (!reciept) {
                    reciept = `<input data-reimId =${rec.id} type = "file" value = "Add Reciept" class = "addReciept" />`
                } else {
                    reciept = `<img src="${baseUrl}/file/${rec.id}"/>`
                }

            } catch (error) {
                console.log(error)
                console.log("could not read file")
            }

            return (`
                <tr>
                    <td>${rec.id}</td>
                    <td>${rec.description}</td>
                    <td>${rec.amount}</td>
                    <td>${new Date(rec.submittedOn)}</td>
                    <td>${rec.type}</td>
                    <td>${rec.status}</td>
                    <td>${rec.status}</td>
                    <td>${ Date(rec.resolved)}</td>
                </tr>
                <tr>
                    <td colspan = "8">Reciept:  ${reciept}</td>
                </tr>
            `)
        })
        tableRows = await Promise.all(tableRows);
        tableRows = tableRows.join('');
    }
    return (`
                <div> <h2 id="empTableTitle">All Reimbursements</h2></div >
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Description</th>
                                <th>Amount</th>
                                <th>Submitted</th>
                                <th>Reciept</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Resloved?</th>
        </tr>
                                ${tableRows}
        </table>
                            `)
}

async function addReciept() {
    try {
        let file = null;

        if ($(this).prop('files').length > 0) {
            file = $(this).prop('files')[0];
        }
        let formData = new FormData();
        formData.append("reimId", $(this).attr("data-reimid"))
        formData.append("reciept", file)
        formData.append("id", "1234")

        let config = {
            type: "POST",
            enctype: 'multipart/form-data',
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
        }

        getAjax(`${baseUrl}/file`, config)
        console.log("after ajax?")
        employeeScreen();
    } catch (ex) {
        console.log(ex)
        console.log("failed to upload file")
    }

}

const employeeEdit = () => {
    let employee = state.activeUser
    $("#employeeMenu").html(employeeNav() + employeeInfoForm(employee))
}
const empInfoSubmit = async () => {
    try {
        let employee = state.activeUser
        employee.lastName = $("#empInfoLastName").val();
        employee.firstName = $("#empInfoFirstName").val();
        employee.email = $("#empInfoEmail").val();
        employee.password = $("#empInfoPassword").val();

        getAjax(`${baseUrl}/employee/${employee.id}`, put(employee));

    } catch (error) {
        console.log(error)
        $("#empInfoMsg").text(error)
    }
}

const submitReimbursement = () => {
    let reimbursement = {
        id: "1234",
        amount: $("#reimAmount").val(),
        description: $("#reimDescription").val(),
        resolvedOn: "1111-11-11",
        resolver: "123",
        status: "pending",
        submittedBy: state.activeUser.id,
        submittedOn: "1111-11-11",
        type: $("#reimType").val()
    }

    getAjax(`${baseUrl}/reimbursement`, post(reimbursement))
        .then(result => {
            employeeScreen();
        })
        .catch(error => {
            console.log(error);
        })
}

const filterEmpTableView = async () => {
    let list = await getAjax(`${baseUrl}/reimbyuser/${state.activeUser.id}`, get)
    let newList = null;
    if (state.tableView === "all") {
        state.tableView = "pending";
        newList = list.filter(item => {
            if (item.status === "pending") {
                return item;
            }
        })
        let table = await employeeTable(newList);
        $("#employeeMenu").html(employeeNav() + employeeForm() + table)
        $("#empTableTitle").text("Pending Reimbursements")
        return
    }

    if (state.tableView === "pending") {
        state.tableView = "resolved";
        newList = list.filter(item => {
            if (item.status === "resolved") {
                return item;
            }
        })
        let table = await employeeTable(newList);
        $("#employeeMenu").html(employeeNav() + employeeForm() + table)
        $("#empTableTitle").text("Resolved Reimbursements")
        return
    }

    if (state.tableView === "resolved") {
        state.tableView = "all";
        let table = await employeeTable(list);
        $("#employeeMenu").html(employeeNav() + employeeForm() + table)
        $("#empTableTitle").text("All Reimbursements")
        return
    }

}