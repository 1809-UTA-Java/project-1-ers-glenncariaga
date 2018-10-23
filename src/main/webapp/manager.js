

//manager Screen
const selectManager = () => {
    managerScreen();
}

const managerScreen = async () => {
    hideAll();
    $("#managerMenu").removeAttr("hidden");
    try {
        let managerList = await getAjax(`${baseUrl}/reimbursement`, get)
        let employeeList = await getAjax(`${baseUrl}/employee`, get)
        hideAll();
        $("#managerMenu").removeAttr("hidden");
        table = await reimTable(managerList);
        employee = employeeListTable(employeeList);
        $("#managerMenu").html(managerNav() + employee + table)
    } catch (error) {
        console.log(error)
    }
}

const managerNav = () => {
    return (`
        <input type = "button" value = "Refresh" id = "managerRefresh" />
        <input type = "button" value = "Logout" id = "managerLogout" />
    `)
}

const employeeListTable = (list) => {
    console.log(list.length, list)
    let tableRows = [];
    if (list != null && list.length > 0) {
        tableRows = list.map(rec => {
            try {
                return (`
                    <tr>
                        <td class = "empId">${ rec.id}</td>
                        <td>${ rec.username}</td>
                        <td>${ rec.firstName}</td>
                        <td>${ rec.lastName}</td>
                        <td>${ rec.email}</td >
                    </tr >
                `)
            } catch (error) {
                console.log(error)
            }
        })

    }
    tableRows = tableRows.join('')
    return (`
    <div> <h2 id="empTable">Employee List</h2></div >
        <table>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name </th>
                <th>Email</th>
            </tr>
                    ${tableRows}
        </table>
                `)
}

const reimTable = async (list) => {
    console.log(list.length, list)
    let tableRows = [];
    if (list != null && list.length > 0) {
        tableRows = list.map(async (rec) => {
            let submittedBy = null;
            let resolver = null
            let reciept = null;
            try {
                submittedBy = await getAjax(`${baseUrl}/employee/${rec.submittedBy}`, get);
                resolver = await getAjax(`${baseUrl}/employee/${rec.resolver}`, get);
            } catch (error) {
                console.log(error)
            }
            try {
                let config = {
                    type: "GET",
                    contentType: "picture/png",
                    processData: false,
                }

                reciept = await getAjax(`${baseUrl}/file/${rec.id}`, config)
                if (!reciept) {
                    reciept = `No receipt submitted.`
                } else {
                    reciept = `<img src="${baseUrl}/file/${rec.id}"/>`
                }
            } catch (error) {
                console.log("get picture Error: ", error)
            }
            if (resolver == null) {
                resolver = {
                    firstName: "",
                    lastName: ""
                }
            }

            if (submittedBy == null) {
                submittedBy = {
                    firstName: "anonymous",
                    lastName: ""
                }
            }

            return (`
                    <tr>
                        <td>${ rec.id}</td>
                        <td>${ rec.description}</td>
                        <td>${ rec.amount}</td>
                        <td>${ new Date(rec.submittedOn)}</td>
                        <td>${ submittedBy.firstName} ${submittedBy.lastName}</td >
                        <td>${ resolver.firstName} ${resolver.lastName}</td>
                        <td>${ rec.type}</td>
                        <td>${ rec.status}</td>
                        <td>${ !rec.resolvedOn ? approveDenyButton(rec.status) : null}</td>
                        <td>${ rec.resolvedOn ? new Date(rec.resolvedOn) : resolveButton(rec)}</td>
                    </tr >
                    <tr>
                    <td colspan ="10">Reciept: ${reciept}</td>
                    </tr>
                `)
        })

    }
    tableRows = await Promise.all(tableRows);
    tableRows = tableRows.join('');
    return (`
    <div> <h2 id="managerEmpTable">All Reimbursements</h2></div >
        <table>
            <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Submitted On </th>
                <th>Author</th>
                <th>Resolver</th>
                <th>Type</th>
                <th>Status</th>
                <th>Action</th>
                <th>Resloved?</th>
            </tr>
                    ${tableRows}
        </table>
                `)
}

async function viewEmployeeReimb() {
    let id = $(this).parent().find(".empId").text();

    try {
        let managerList = await getAjax(`${baseUrl}/reimbursement/${id}`, get)
        let employeeList = await getAjax(`${baseUrl}/employee`, get)
        hideAll();
        $("#managerMenu").removeAttr("hidden");
        table = await reimTable(managerList);
        employee = employeeListTable(employeeList);
        $("#managerMenu").html(managerNav() + employee + table)
    } catch (error) {
        console.log(error)
    }
}

const approveDenyButton = (status) => {
    return (
        status === "denied" ?
            `<input value="Approve" type="button" class="reimDecision" />` :
            `<input value="Deny" type="button" class="reimDecision" />`
    )
}

function changeDecision() {
    console.log($(this).val())
    if ($(this).val() === "Approve") {
        $(this).val("Deny");
    } else {
        $(this).val("Approve");
    }
}

async function resolveDecision() {
    let data = $(this).attr('data-reim');
    console.log(data);
    data = JSON.parse(data);
    data.resolver = state.activeUser.id;
    data.resolvedOn = "1540076251000";
    decision = $(this).parent().parent().find(".reimDecision").val();
    console.log(decision);
    if (decision === "Deny") {
        data.status = "denied";
    } else {
        data.status = "approved";
    }
    try {
        await getAjax(`${baseUrl}/reimbursement/${data.id}`, put(data))
    } catch (error) {
        console.log(error)
    }
    $(this).parent().html('');
}

const resolveButton = (rec) => {
    rec = JSON.stringify(rec);
    return (
        `<input type="button" value="Resolve" data-reim='${rec}' class ="resolve"/>
            `)
}