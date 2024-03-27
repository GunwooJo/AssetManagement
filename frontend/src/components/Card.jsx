import * as React from 'react';
import { Button, Card, Text } from 'react-native-paper';

const MyComponent = () => (
  <Card style={{ margin : 10 }}>
    <Card.Title
    title="Card Title"
    subtitle="Card Subtitle"
    right={(props) => <Button {...props} icon="camera" onPress={() => {}} />}
    />
    <Card.Title
    title="Card Title"
    subtitle="Card Subtitle"
    right={(props) => <Button {...props} icon="camera" onPress={() => {}} />}
    />
  </Card>
);

export default MyComponent;