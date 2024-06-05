import * as React from 'react';
import { ScrollView, View } from 'react-native';
import PChart from '../components/PieChart';
import { IconButton, Text } from 'react-native-paper';
import BChart from '../components/BarChart';
import { barData, pieData } from '../utils/SampleData';
import arrow_fw from '../assets/arrow_fw.svg'
import arrow_bc from '../assets/arrow_bc.svg'

export default function Asset() {
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
    <View>
      <ScrollView>
        <View style={{ marginLeft: 10, marginRight: 10 }}>
          <View style={{ flexDirection: 'row', marginTop: 10, alignSelf: 'center', alignItems: 'center' }}>
            <IconButton icon={arrow_bc} onPress={() => {setMonth(month - 1)}}/>
            <Text variant='headlineMedium'>{month}월</Text>
            <IconButton icon={arrow_fw} onPress={() => {setMonth(month + 1)}}/>
          </View>
          <Text variant='displaySmall' style={{ marginBottom: 15, alignSelf: 'center' }}>XXX,XXX,XXX 원</Text>
          <View style={{ flexDirection: 'row', marginBottom: 15}}>
            <PChart data={pieData}/>
            <View style={{ flexDirection: 'column', alignSelf: 'center' }}>
              {Object.values(pieData).map((v, k) => { return (
                <View key={k} style={{ flexDirection: 'row' }}>
                  <Text key={v.color} variant='headlineSmall'
                  style={{ color: v.color, marginLeft: 10, backgroundColor: "black" }}>X %</Text>
                  <Text key={v.text} variant='headlineSmall'>{v.text}</Text>
                </View>
              )})}
            </View>
          </View>
          <View style={{ alignSelf: 'center', marginRight: 15 }}>
            <BChart data={barData}/>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}