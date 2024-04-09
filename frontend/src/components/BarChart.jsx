import * as React from 'react';
import { View } from 'react-native';
import { BarChart } from "react-native-gifted-charts";

export default function BChart({data}) {
  const a = 11
  return (
    <View>
      <BarChart
            data={data}
            rulesThickness={3}
            yAxisThickness={2}
            xAxisThickness={2}
            maxValue={400}
            noOfSections={5}
            barWidth={35}
            showLine
            disablePress
            disableScroll
            />
    </View>
  )
}