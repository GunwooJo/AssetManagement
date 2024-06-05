import hn_bank from '../assets/hn_bank.svg'
import kb_bank from '../assets/kb_bank.svg'
import ma_stock from '../assets/ma_stock.svg'
import nh_bank from '../assets/nh_bank.svg'
import nh_stock from '../assets/nh_stock.svg'
import ss_stock from '../assets/ss_stock.svg'
import minus from '../assets/minus.svg'
import blank from '../assets/blank.svg'
import success from '../assets/success.svg'
import failure from '../assets/failure.svg'

export const HomeData1 = [
    {
        "id" : 1,
        "type" : "head",
        "button" : false,
        "mainText" : "나의 자산",
        "subText" : "XXX,XXX,XXX 원"
        //"stackPage" : "pagename"
        //"image" : "img/url"
    },
    {
        "id" : 2,
        "type" : "body",
        "button" : false,
        "mainText" : "하나 은행",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : hn_bank
    },
    {
        "id" : 3,
        "type" : "body",
        "button" : false,
        "mainText" : "NH 증권",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : nh_stock
    },
    {
        "id" : 4,
        "type" : "body",
        "button" : false,
        "mainText" : "삼성 증권",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : ss_stock
    },
    {
        "id" : 5,
        "type" : "foot",
        "button" : true,
        "mainText" : "모두보기",
        //"subText" : "subtext"
        "stackPage" : "AccountList"
        //"image" : "img/url" 
    }
];
export const HomeData2 = [
    {
        "id" : 1,
        "type" : "head",
        "button" : false,
        "mainText" : "이번 달 지출",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "ExpenseList"
        //"image" : "img/url"
    },
    {
        "id" : 2,
        "type" : "body",
        "button" : false,
        "mainText" : "오늘 지출",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : minus
    },
    {
        "id" : 3,
        "type" : "body",
        "button" : true,
        "mainText" : "고정 지출",
        "subText" : "XXX,XXX,XXX 원",
        "stackPage" : "ExpenseList",
        "image" : minus
    }
];
export const pieData = [
    {value: 40, color: '#ABDEE6', text: "현금"},
    {value: 30, color: '#CBAACB', text: "주식"},
    {value: 15, color: '#FFFFB5', text: "채권"},
    {value: 10, color: '#FFCCB6', text: "부동산"},
    {value: 5, color: '#F3B0C3', text: "기타"}
];
export const barData = [
    {
      value: 230,
      label: '23.12',
      frontColor: '#4ABFF4',
      sideColor: '#23A7F3',
      topColor: '#92e6f6',
    },
    {
      value: 180,
      label: '24.01',
      frontColor: '#79C3DB',
      sideColor: '#68BCD7',
      topColor: '#9FD4E5',
    },
    {
      value: 195,
      label: '24.02',
      frontColor: '#28B2B3',
      sideColor: '#0FAAAB',
      topColor: '#66C9C9',
    },
    {
      value: 250,
      label: '24.03',
      frontColor: '#4ADDBA',
      sideColor: '#36D9B2',
      topColor: '#7DE7CE',
    },
    {
      value: 320,
      label: '24.04',
      frontColor: '#91E3E3',
      sideColor: '#85E0E0',
      topColor: '#B0EAEB',
    }
];
export const AccountListData = [
    {
        "id" : 1,
        "type" : "foot",
        "button" : true,
        "mainText" : "기관 추가하기",
        //"subText" : "subtext"
        "stackPage" : "AccountRegister"
        //"image" : "img/url"
    },
    {
        "id" : 2,
        "type" : "body",
        "button" : false,
        "mainText" : "국민 은행",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : kb_bank
    },
    {
        "id" : 3,
        "type" : "body",
        "button" : false,
        "mainText" : "농협 은행",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : nh_bank
    },
    {
        "id" : 4,
        "type" : "body",
        "button" : false,
        "mainText" : "하나 은행",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : hn_bank
    },
    {
        "id" : 5,
        "type" : "body",
        "button" : false,
        "mainText" : "NH 증권",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : nh_stock
    },
    {
        "id" : 6,
        "type" : "body",
        "button" : false,
        "mainText" : "미래에셋 증권",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : ma_stock
    },
    {
        "id" : 7,
        "type" : "body",
        "button" : false,
        "mainText" : "삼성 증권",
        "subText" : "XXX,XXX,XXX 원",
        //"stackPage" : "pagename"
        "image" : ss_stock
    }
];
export const AccountRegisterData = [
    {
        "id" : 1,
        "type" : "body",
        "button" : true,
        "mainText" : "국민은행(KB)",
        "subText" : "연동하기",
        "stackPage" : "국민은행(KB)",
        "image" : kb_bank
    },
    {
        "id" : 2,
        "type" : "body",
        "button" : true,
        "mainText" : "농협은행(NH)",
        "subText" : "연동하기",
        "stackPage" : "농협은행(NH)",
        "image" : nh_bank
    },
    {
        "id" : 3,
        "type" : "body",
        "button" : true,
        "mainText" : "하나은행",
        "subText" : "연동하기",
        "stackPage" : "하나은행",
        "image" : hn_bank
    },
    {
        "id" : 4,
        "type" : "body",
        "button" : true,
        "mainText" : "나무(NH투자증권)",
        "subText" : "연동하기",
        "stackPage" : "나무(NH투자증권)",
        "image" : nh_stock
    },
    {
        "id" : 5,
        "type" : "body",
        "button" : true,
        "mainText" : "미래에셋증권",
        "subText" : "연동하기",
        "stackPage" : "미래에셋증권",
        "image" : ma_stock
    },
    {
        "id" : 6,
        "type" : "body",
        "button" : true,
        "mainText" : "삼성증권",
        "subText" : "연동하기",
        "stackPage" : "삼성증권",
        "image" : ss_stock
    }
];
export const DAWData1 = [
    {
        "id" : 1,
        "type" : "body",
        "button" : false,
        "mainText" : "XX 음식점",
        "subText" : "- XXX,XXX원",
        "image" : blank
    },
    {
        "id" : 2,
        "type" : "body",
        "button" : false,
        "mainText" : "XX 편의점",
        "subText" : "- XX,XXX원",
        "image" : blank
    },
];
export const ChallengeData1 = [
    {
        "id" : 1,
        "type" : "body",
        "button" : false,
        "mainText" : "2024년 5월",
        "subText" : "도전 : XXX,XXX원",
        "image" : blank
    }
];
export const ChallengeData2 = [
    {
        "id" : 1,
        "type" : "body",
        "button" : false,
        "mainText" : "2024년 4월",
        "subText" : "도전 : XXX,XXX원",
        "image" : failure
    }
];
export const ChallengeData3 = [
    {
        "id" : 1,
        "type" : "body",
        "button" : false,
        "mainText" : "2024년 3월",
        "subText" : "도전 : XXX,XXX원",
        "image" : success
    }
];
export const ExpenseListData = [
    {
        "id" : 1,
        "type" : "body",
        "button" : false,
        "mainText" : "고정 지출1",
        "subText" : "XXX,XXX 원",
        //"stackPage" : "ExpenseList"
        "image" : minus
    },
    {
        "id" : 2,
        "type" : "body",
        "button" : false,
        "mainText" : "고정 지출2",
        "subText" : "XXX,XXX 원",
        //"stackPage" : "ExpenseList"
        "image" : minus
    },
    {
        "id" : 3,
        "type" : "body",
        "button" : false,
        "mainText" : "고정 지출3",
        "subText" : "XXX,XXX 원",
        //"stackPage" : "ExpenseList"
        "image" : minus
    }
];
export const ConversationalAIData = [
    {
        "id" : 1,
        "type" : "user",
        "text" : "Hello!\nI am USER!!!",
        "isLoading" : false
    },
    {
        "id" : 2,
        "type" : "bot",
        "text" : "Hello! USER~",
        "isLoading" : false
    },
    {
        "id" : 3,
        "type" : "user",
        "text" : "1\n2\n3\n4\n5",
        "isLoading" : false
    },
    {
        "id" : 4,
        "type" : "bot",
        "text" : "loading~",
        "isLoading" : true
    },
];