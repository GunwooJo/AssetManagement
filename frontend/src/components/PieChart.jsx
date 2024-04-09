import * as React from 'react';
import { View } from 'react-native';
import { PieChart } from "react-native-gifted-charts";

export default function PChart({data}) {
  return (
    <View>
      <PieChart
        donut
        data={data}
        textSize={20}
        textColor="black"
        radius={100}
        innerRadius={70}
        //focusOnPress
      />
    </View>
  )
}