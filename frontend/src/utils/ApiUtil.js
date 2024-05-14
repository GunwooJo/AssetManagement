export const BaseUrl = "http://localhost:8080"

export const nameRegex = /^[가-힣]{2,10}$/
export const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
export const passwordRegex = /^[a-zA-Z0-9]{4,10}$/
export const birthdayRegex = /^(0[1-9]|[1-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/

export const AccountRegisterDetailData = [
    {   // 국민은행
        "businessType": "BK",
        "loginType": "1",
        "organization": "0004",
        "clientType": "P",
        "id": "",
        "password": ""
    },
    {   // 농협은행
        "businessType": "BK",
        "loginType": "1",
        "organization": "0011",
        "clientType": "P",
        "id": "",
        "password": ""
    },
    {   // 하나은행
        "businessType": "BK",
        "loginType": "1",
        "organization": "0081",
        "clientType": "P",
        "id": "",
        "password": ""
    },
    {   // 나무(NH투자증권)
        "businessType": "ST",
        "loginType": "1",
        "organization": "1247",
        "clientType": "A",
        "id": "",
        "password": ""
    },
    {   // 미래에셋증권
        "businessType": "ST",
        "loginType": "1",
        "organization": "0238",
        "clientType": "A",
        "id": "",
        "password": ""
    },
    {   // 삼성증권
        "businessType": "ST",
        "loginType": "1",
        "organization": "0240",
        "clientType": "A",
        "id": "",
        "password": ""
    }
]
