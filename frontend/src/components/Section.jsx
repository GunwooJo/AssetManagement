import * as React from 'react';
import { View } from 'react-native';
import { Button, Card, Icon } from 'react-native-paper';

function RightContent(btn) {
  if(btn == true) {
    return (<Button icon="folder"/>);
  }
}

const Section = ({data}) => (
  <View style={{ padding: 5, margin: 20 }}>
    <Card>
      {Object.values(data).map((v, k) => {
        if (v[0] == 'head') {
          return (
            <Card.Title key={k} title={v[2]} subtitle={v[3]}
            titleVariant='headlineLarge' subtitleVariant='headlineSmall'
            right={() => RightContent(v[1])}
            style={{ paddingTop: 10 }}/>
          )}
        else if (v[0] == 'body') {
          return (
            <Card.Title key={k} title={v[2]} subtitle={v[3]}
            titleVariant='titleLarge' subtitleVariant='titleMedium'
            left={p => <Icon {...p} source={v[4]}/>}
            right={() => RightContent(v[1])}/>
          )
        }
        else {
          return (
            <Card.Title key={k} title={v[2]}
            titleVariant='headlineMedium'
            right={() => RightContent(v[1])}/>
          )
        }
        })}
    </Card>
  </View>
);

export default Section;