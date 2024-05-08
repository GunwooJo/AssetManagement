import React from "react";
import { View } from 'react-native';
import { Button, Text } from 'react-native-paper';
import PChart from "../components/PieChart";
import { pieData } from "../utils/SampleData";

export default function Category() {
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
    <View style={{ margin: 20 }}>
      <View style={{ flexDirection: 'row', marginBottom: 15, alignSelf: 'center'}}>
        <Button icon='folder' onPress={() => {setMonth(month - 1)}}/>
        <Text variant='headlineMedium'>{month}월</Text>
        <Button icon='folder' onPress={() => {setMonth(month + 1)}}/>
      </View>
      <View style={{ alignSelf: "center" }}>
        <PChart data={pieData}/>
      </View>
      <View style={{ alignSelf: "center" }}>
        <Text variant='headlineMedium'>AAA : XX%</Text>
        <Text variant='headlineMedium'>AAA : XX%</Text>
        <Text variant='headlineMedium'>AAA : XX%</Text>
        <Text variant='headlineMedium'>AAA : XX%</Text>
      </View>
    </View>
  );
}