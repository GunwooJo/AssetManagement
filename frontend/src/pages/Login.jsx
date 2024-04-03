import * as React from "react";
import { View } from 'react-native';
import { Button, List, Text, TextInput } from "react-native-paper";

export default function Login({ navigation }) {
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');

  return (
    <View style={{ margin: 20 }}>
      <Text variant="displayMedium"
        style={{ fontWeight: 'bold' , textAlign: 'center', marginBottom: 20 }}>로그인</Text>
      <TextInput label='아이디(이메일)' value={email} placeholder="example@example.com"
        mode="outlined" onChangeText={email => setEmail(email)}
        keyboardType="email-address" autoCapitalize="none"
        returnKeyType="next" style={{ marginBottom: 15 }}/>
      <TextInput label='비밀번호' value={password} placeholder="********"
        mode="outlined" onChangeText={password => setPassword(password)}
        keyboardType="default" secureTextEntry={true} style={{ marginBottom: 20 }}/>
      <Button mode="contained" onPress={() => console.log('로그인')}>
        <Text variant="displayMedium" style={{ color: '#ffffff', fontWeight: 'bold' }}>로그인</Text></Button>
      <List.Section>
        <List.Item title='회원가입' onPress={() => navigation.navigate('Register')}/>
        <List.Item title='아이디 찾기' onPress={() => console.log('아이디 찾기')}/>
        <List.Item title='비밀번호 찾기' onPress={() => console.log('비밀번호 찾기')}/>
      </List.Section>
    </View>
  );
}