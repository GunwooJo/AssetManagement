import * as React from 'react';
import { View } from 'react-native';
import { Button, Card, Text, Avatar, Icon } from 'react-native-paper';

function RightContent(btn) {
  if(btn == true) {
    return (<Button icon="folder"/>);
  }
}

const Section = ({data}) => (
  <View style={{ padding: 5, margin: 20 }}>
    <Card style={{ flex: 1 }}>
      {Object.values(data).map((v) => {
        if (v[0] == 'head') {
          return (
            <Card.Title title={v[2]} subtitle={v[3]}
            titleVariant='headlineLarge' subtitleVariant='headlineSmall'
            right={p => RightContent(v[1])}
            style={{paddingTop: 10}}/>
          )}
        else if (v[0] == 'body') {
          return (
            <Card.Title title={v[2]} subtitle={v[3]}
            titleVariant='titleLarge' subtitleVariant='titleMedium'
            left={p => <Icon {...p} source={v[4]}/>}
            right={p => RightContent(v[1])}/>
          )
        }
        else {
          return (
            <Card.Title title={v[2]}
            titleVariant='headlineMedium'
            right={p => RightContent(v[1])}/>
          )
        }
        })}
    </Card>
  </View>
);

export default Section;