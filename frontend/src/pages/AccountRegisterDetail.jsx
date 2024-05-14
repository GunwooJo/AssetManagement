import * as React from "react";
import { View } from 'react-native';
import { Button, Text, TextInput } from "react-native-paper";
import { AccountRegisterDetailData } from "../utils/ApiUtil";

export default function AccountRegisterDetail({ route }) {
  const [accountid, setAccountId] = React.useState('');
  const [password, setPassword] = React.useState('');
  let reqData = {}

  React.useEffect(() => {
    let organizationCode;
    if(route.name == "국민은행(KB)") {
        organizationCode = "0004";
    }
    else if(route.name == "농협은행(NH)") {
        organizationCode = "0011";
    }
    else if(route.name == "하나은행") {
        organizationCode = "0081";
    }
    else if(route.name == "나무(NH투자증권)") {
        organizationCode = "1247";
    }
    else if(route.name == "미래에셋증권") {
        organizationCode = "0238";
    }
    else if(route.name == "삼성증권") {
        organizationCode = "0240";
    }
    Object.values(AccountRegisterDetailData).map((v) => {
        if(v.organization == organizationCode) {
            reqData = v;
        }
    })
  }, [])

  return (
    <View style={{ margin: 20 }}>
      <Text variant="displayMedium"
        style={{ fontWeight: 'bold' , textAlign: 'center', marginBottom: 20 }}>{route.name}</Text>

      <TextInput label='기관 아이디' value={accountid} placeholder=""
        mode="outlined" onChangeText={accountid => setAccountId(accountid)}
        keyboardType="email-address" autoCapitalize="none" activeOutlineColor="black"
        returnKeyType="next" style={{ marginBottom: 15 }}/>

      <TextInput label='비밀번호' value={password} placeholder="********"
        mode="outlined" onChangeText={password => setPassword(password)}
        keyboardType="default" autoCapitalize="none" activeOutlineColor="black"
        secureTextEntry={true} style={{ marginBottom: 20 }}/>

      <Button mode="contained" buttonColor="#3B3FF6" onPress={() => {}}>
        <Text variant="displayMedium" style={{ color: '#ffffff', fontWeight: 'bold' }}>연동하기</Text></Button>
    </View>
  );
}