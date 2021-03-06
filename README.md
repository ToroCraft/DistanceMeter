
# Distance Meter
This Minecraft Forge mod provides a HUD next to the reticle showing the distance to the block that is being pointed at.

### [Download from here](https://minecraft.curseforge.com/projects/distance-meter)


## Development Environment Setup
Download the desired version of Forge MDK from https://files.minecraftforge.net/ and unzip the MDK into a new dirctory. After the MDK is unzipped, remove the `main` folder from the `src` folder and clone this repo into the `src` directory as `main`. Then you will need to either copy or link the `build.gradle` from the repository to the root of the MDK, replacing the original one. 

### Setup Example
Replace `<MC_VERSION>` with the Minecraft version of the MDK (for example `~/mdk_1.10.2`) and `<MDK_FILE>` with the file name of the MDK you downloaded (for example `forge-1.10.2-12.18.2.2099-mdk.zip`)

```
mkdir ~/mdk_<MC_VERSION>
cd ~/mdk_<MC_VERSION>
cp <MDK_FILE> .
unzip <MDK_FILE>
rm -rf src/main
git clone https://github.com/ToroCraft/DistanceMeter.git
mv build.gradle build.default.gradle
ln -s src/main/build.gradle build.gradle
./gradlew setupDecompWorkspace
./gradlew eclipse
```
