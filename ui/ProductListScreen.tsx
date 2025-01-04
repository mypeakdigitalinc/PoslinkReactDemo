import { Button } from "react-native";

const ProductListScreen = ({navigation}:{navigation:any}) => {
    return (
      <Button
        title="Go to Jane's profile"
        onPress={() =>
          navigation.navigate('Profile', {name: 'Jane'})
        }
      />
    );
  };
export default ProductListScreen;