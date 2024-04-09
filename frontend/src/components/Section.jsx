import * as React from 'react';
import { View } from 'react-native';
import { Button, Card, Icon } from 'react-native-paper';

function RightContent(btn, navigation, page) {
  if(btn == true) {
    return (<Button icon="image" onPress={() => {navigation.navigate(page)}}/>);
  }
}

const Section = ({data, navigation}) => (
  <View style={{ padding: 5, margin: 20 }}>
    <Card>
      {Object.values(data).map((v) => {
        if (v.type == "head") {
          return (
            <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
            titleVariant='headlineLarge' subtitleVariant='headlineSmall'
            right={() => RightContent(v.button, navigation, v.stackPage)}
            style={{ paddingTop: 10 }}/>
          )
        }
        else if (v.type == 'body') {
          return (
            <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
            titleVariant='titleLarge' subtitleVariant='titleMedium'
            left={p => <Icon {...p} source="image"/>}
            right={() => RightContent(v.button, navigation, v.stackPage)}/>
          )
        }
        else {
          return (
            <Card.Title key={v.id} title={v.mainText}
            titleVariant='headlineMedium'
            right={() => RightContent(v.button, navigation, v.stackPage)}/>
          )
        }
      })}
    </Card>
  </View>
);

export default Section;