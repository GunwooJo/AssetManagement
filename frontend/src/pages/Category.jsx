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
      <View style={{ alignSelf: "center", marginTop: 10 }}>
        <Text variant='headlineMedium'>식비 : <Text style={{ color: "#ABDEE6", backgroundColor: "black" }}>45%</Text></Text>
        <Text variant='headlineMedium'>교통 : <Text style={{ color: "#CBAACB", backgroundColor: "black" }}>30%</Text></Text>
        <Text variant='headlineMedium'>생활 : <Text style={{ color: "#FFFFB5", backgroundColor: "black" }}>15%</Text></Text>
        <Text variant='headlineMedium'>여가 : <Text style={{ color: "#FFCCB6", backgroundColor: "black" }}>10%</Text></Text>
        <Text variant='headlineMedium'>기타 : <Text style={{ color: "#F3B0C3", backgroundColor: "black" }}>5%</Text></Text>
      </View>
    </View>
  );
}