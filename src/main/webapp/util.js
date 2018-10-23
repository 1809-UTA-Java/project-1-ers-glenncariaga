
//AJAX config

const baseUrl = "http://localhost:8080";

const getAjax = async (url, config) => {
    try {
        let result = await $.ajax(url, config);
        return result
    } catch (ex) {
        return ex
    }
}

const get = {
    cache: 'false',
    contentType: 'application/json',
    dataType: "JSON",
    type: "GET",
}

const post = (data) => {
    data = JSON.stringify(data);
    console.log(data)
    return ({
        cache: 'false',
        contentType: 'application/json',
        dataType: "JSON",
        data: data,
        type: "POST"
    })
}

const put = (data) => {
    data = JSON.stringify(data);
    console.log(data)
    return ({
        cache: 'false',
        contentType: 'application/json',
        dataType: "JSON",
        data: data,
        type: "PUT"
    })
}

