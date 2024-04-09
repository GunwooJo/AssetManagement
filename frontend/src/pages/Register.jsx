import * as React from "react";
import { Keyboard, ScrollView, View } from 'react-native';
import { Button, Text, TextInput } from "react-native-paper";
import Load from "../components/Load";
import axios from 'axios';
import {BaseUrl} from "../utils/ApiUtil";

export default function Register() {
  const [name, setName] = React.useState('');
  const [age, setAge] = React.useState('');
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');
  //const [isKeyboardVisible, setKeyboardVisible] = React.useState(false);
  const [isLoading, setIsLoading] = React.useState(false);
/*
  React.useEffect(() => {
    const keyboardDidShowListener = Keyboard.addListener(
      'keyboardDidShow', () => {setKeyboardVisible(true)}
    );
    const keyboardDidHideListener = Keyboard.addListener(
      'keyboardDidHide', () => {setKeyboardVisible(false)}
    );
    return () => {
      keyboardDidShowListener.remove();
      keyboardDidHideListener.remove();
    };
  }, []);

  <View style={[{ margin: 20 }, isKeyboardVisible ? { flex: 0.75 } : { flex: 0.9 }]}>
*/
  const aa = async() => {
    try {
      const res = await axios.post(BaseUrl + '/member/register', {
        login_id: email,
        password: password,
        name: name,
        birthday: age
      });
      console.log(res);
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <View style={{ margin: 20 }}>
      {isLoading ?
      <Load/>
      :
      <View>
      <Text variant="displayMedium"
      style={{ fontWeight: 'bold' , textAlign: 'center', marginBottom: 20 }}>회원가입</Text>
      <ScrollView>
      <Text style={{ fontWeight: 'bold', marginBottom: 5}}>이름</Text>
      <TextInput value={name} placeholder="이름을 입력해주세요."
      mode="outlined" onChangeText={name => setName(name)}
      keyboardType="email-address" autoCapitalize="none"
      returnKeyType="next" style={{ marginBottom: 10 }}
      blurOnSubmit={false}/>
        
      <Text style={{ fontWeight: 'bold', marginBottom: 5 }}>아이디(이메일)</Text>
      <TextInput value={email} placeholder="example@example.com"
      mode="outlined" onChangeText={email => setEmail(email)}
      keyboardType="email-address" autoCapitalize="none"
      returnKeyType="next" style={{ marginBottom: 10 }}/>
        
      <Text style={{ fontWeight: 'bold', marginBottom: 5 }}>비밀번호</Text>
      <TextInput value={password} placeholder="********"
      mode="outlined" onChangeText={password => setPassword(password)}
      keyboardType="default" autoCapitalize="none"
      returnKeyType="next" secureTextEntry={true} style={{ marginBottom: 10 }}/>

      <Text style={{ fontWeight: 'bold', marginBottom: 5 }}>생년월일</Text>
      <TextInput value={age} placeholder="YYMMDD"
      mode="outlined" onChangeText={age => setAge(age)}
      keyboardType="number-pad" autoCapitalize="none"
      returnKeyType="done" style={{ marginBottom: 10 }}/>
      </ScrollView>
      
      <Button mode="contained" onPress={aa}>
      <Text variant="displayMedium" style={{ color: '#ffffff', fontWeight: 'bold' }}>회원가입</Text></Button>
      </View>
      }
    </View>
    );
  }