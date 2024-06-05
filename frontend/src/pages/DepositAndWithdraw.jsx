import React from "react";
import { View } from 'react-native';
import { IconButton, Text } from 'react-native-paper';
import { DAWData1 } from "../utils/SampleData";
import Section from "../components/Section";
import arrow_fw from '../assets/arrow_fw.svg'
import arrow_bc from '../assets/arrow_bc.svg'

export default function DepositAndWithdraw() {
  const today = new Date();
  //const year = today.getFullYear();
  const nowMonth = today.getMonth() + 1;
  const [month, setMonth] = React.useState(nowMonth);
  React.useEffect(() => {
    //console.log("월 바꾸기");
  }, [month])
  React.useEffect(() => {
    //console.log("go!");
    return() => {
      //console.log("end!");
    }
  }, [])
  return (
    <View style={{ flexDirection: 'column' }}>
      <View style={{ flexDirection: 'row', marginTop: 10, alignSelf: 'center', alignItems: 'center'}}>
        <IconButton icon={arrow_bc} onPress={() => {setMonth(month - 1)}}/>
        <Text variant='headlineMedium'>{month}월</Text>
        <IconButton icon={arrow_fw} onPress={() => {setMonth(month + 1)}}/>
      </View>
      <View>
          <Text variant='titleLarge' style={{ marginTop: 15, marginLeft: 20 }}>- 5/03</Text>
          <Section data={DAWData1} type="none"/>
      </View>
      <View>
          <Text variant='titleLarge' style={{ marginTop: 15, marginLeft: 20 }}>- 5/01</Text>
          <Section data={DAWData1} type="none"/>
      </View>
    </View>
  );
}