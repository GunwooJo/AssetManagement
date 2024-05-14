import * as React from 'react';
import { View } from 'react-native';
import Section from '../components/Section';
import { AccountRegisterData } from '../utils/SampleData';

export default function AccountRegister({ navigation }) {
  return(
    <View>
      <Section data={AccountRegisterData} navigation={navigation} type="elevated"/>
    </View>
  );
}