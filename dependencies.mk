#dependencies
#top
bin/kuro/amaktet/Configuration.class:

bin/kuro/amaktet/Controller.class: \
	bin/kuro/amaktet/Gui.class \
	bin/kuro/amaktet/Render.class \
	bin/kuro/amaktet/ConsoleManager.class \
	bin/kuro/amaktet/util/event/CommandEvent.class \
	bin/kuro/amaktet/util/event/CommandListener.class

bin/kuro/amaktet/ConsoleManager.class: \
	bin/kuro/amaktet/util/Console.class \
	bin/kuro/amaktet/util/event/CommandEvent.class \
	bin/kuro/amaktet/util/event/CommandListener.class

bin/kuro/amaktet/Driver.class: \
	bin/kuro/amaktet/GameLoader.class

bin/kuro/amaktet/Game.class: \
	bin/kuro/amaktet/ResourceManager.class \
	bin/kuro/amaktet/asset/Cursor.class \
	bin/kuro/amaktet/game/Entity.class \
	bin/kuro/amaktet/game/Map.class \
	bin/kuro/amaktet/asset/Sprite.class \
	bin/kuro/amaktet/asset/Tile.class \
	bin/kuro/amaktet/asset/TileSet.class \
	bin/kuro/amaktet/game/View.class \
	bin/kuro/amaktet/resource/TextureResource.class \
	bin/kuro/amaktet/util/Timer.class

bin/kuro/amaktet/GameExecutionThread.class: \
	bin/kuro/amaktet/Controller.class \
	bin/kuro/amaktet/Game.class \
	bin/kuro/amaktet/Gui.class \
	bin/kuro/amaktet/Render.class \
	bin/kuro/amaktet/util/Timer.class

bin/kuro/amaktet/GameLoader.class: \
	bin/kuro/amaktet/Configuration.class \
	bin/kuro/amaktet/Controller.class \
	bin/kuro/amaktet/Game.class \
	bin/kuro/amaktet/GameExecutionThread.class \
	bin/kuro/amaktet/Gui.class \
	bin/kuro/amaktet/Render.class \
	bin/kuro/amaktet/ResourceManager.class \
	bin/kuro/amaktet/util/NullOutputStream.class \
	bin/kuro/amaktet/util/Timer.class

bin/kuro/amaktet/Gui.class: \
	bin/kuro/amaktet/gui/CustomCardLayout.class

bin/kuro/amaktet/Render.class: \
	bin/kuro/amaktet/Game.class \
	bin/kuro/amaktet/asset/Cursor.class \
	bin/kuro/amaktet/asset/Sprite.class \
	bin/kuro/amaktet/asset/TileSet.class \
	bin/kuro/amaktet/game/View.class

bin/kuro/amaktet/ResourceManager.class: \
	bin/kuro/amaktet/resource/Resource.class \
	bin/kuro/amaktet/resource/ResourceList.class \
	bin/kuro/amaktet/resource/event/ResourceEvent.class \
	bin/kuro/amaktet/resource/event/ResourceListener.class

bin/kuro/amaktet/ConsoleManager.class: \
	bin/kuro/amaktet/util/Console.class \
	bin/kuro/amaktet/util/event/CommandEvent.class \
	bin/kuro/amaktet/util/event/CommandListener.class

#asset package

bin/kuro/amaktet/asset/Cursor.class: \
	bin/kuro/amaktet/game/Entity.class \
	bin/kuro/amaktet/asset/Tile.class \
	bin/kuro/amaktet/math/KVector.class \
	bin/kuro/amaktet/math/Direction.class \
	bin/kuro/amaktet/resource/TextureResource.class

bin/kuro/amaktet/asset/Tile.class: \
	bin/kuro/amaktet/resource/TextureResource.class

bin/kuro/amaktet/asset/Sprite.class: \
	bin/kuro/amaktet/resource/ResourceList.class \
	bin/kuro/amaktet/resource/ResourceContainer.class \
	bin/kuro/amaktet/resource/TextureResource.class \
	bin/kuro/amaktet/resource/JSONResource.class

bin/kuro/amaktet/asset/TileSet.class: \
	bin/kuro/amaktet/asset/Tile.class \
	bin/kuro/amaktet/resource/ResourceList.class \
	bin/kuro/amaktet/resource/ResourceContainer.class \
	bin/kuro/amaktet/resource/TextureResource.class \
	bin/kuro/amaktet/resource/JSONResource.class

#game package
bin/kuro/amaktet/game/Cell.class: \
	bin/kuro/amaktet/game/Entity.class \
	bin/kuro/amaktet/asset/Tile.class \
	bin/kuro/amaktet/math/Direction.class \

bin/kuro/amaktet/game/Entity.class:

bin/kuro/amaktet/game/Map.class: \
	bin/kuro/amaktet/game/Cell.class \
	bin/kuro/amaktet/math/Dimension3D.class \
	bin/kuro/amaktet/resource/JSONResource.class

bin/kuro/amaktet/game/View.class: \
	bin/kuro/amaktet/math/KDimension.class \
	bin/kuro/amaktet/math/KVector.class

#gui package
bin/kuro/amaktet/gui/CustomCardLayout.class:

#math package
bin/kuro/amaktet/math/Direction.class:

bin/kuro/amaktet/math/Dimension3D.class:

bin/kuro/amaktet/math/KDimension.class:

bin/kuro/amaktet/math/KVector.class:

#resource package
bin/kuro/amaktet/resource/JSONResource.class: \
	bin/kuro/amaktet/resource/TextResource.class

bin/kuro/amaktet/resource/Resource.class: \
	bin/kuro/amaktet/resource/event/ResourceEvent.class \
	bin/kuro/amaktet/resource/event/ResourceListener.class \
	bin/kuro/amaktet/resource/ResourceLoadException.class

bin/kuro/amaktet/resource/ResourceList.class: \
	bin/kuro/amaktet/resource/ResourceContainer.class \

bin/kuro/amaktet/resource/ResourceContainer.class: \

bin/kuro/amaktet/resource/TextResource.class: \
	bin/kuro/amaktet/resource/Resource.class

bin/kuro/amaktet/resource/TextureResource.class: \
	bin/kuro/amaktet/resource/Resource.class

#resource.event package
bin/kuro/amaktet/resource/event/ResourceEvent.class:

bin/kuro/amaktet/resource/event/ResourceListener.class: \
	bin/kuro/amaktet/resource/event/ResourceEvent.class

#test package
bin/kuro/amaktet/test/TestConsole.class: \
	bin/kuro/amaktet/ConsoleManager.class \
	bin/kuro/amaktet/util/Console.class \
	bin/kuro/amaktet/util/event/CommandEvent.class \
	bin/kuro/amaktet/util/event/CommandListener.class

bin/kuro/amaktet/test/TestInputStreamHandler.class: \
	bin/kuro/amaktet/util/InputStreamEvent.class \
	bin/kuro/amaktet/util/InputStreamHandler.class \
	bin/kuro/amaktet/util/InputStreamListener.class

#util package
bin/kuro/amaktet/util/Console.class: \
	bin/kuro/amaktet/util/event/CommandEvent.class \
	bin/kuro/amaktet/util/event/CommandListener.class

bin/kuro/amaktet/util/NullOutputStream.class: \

bin/kuro/amaktet/util/Timer.class: \

bin/kuro/amaktet/util/InputStreamEvent.class: \

bin/kuro/amaktet/util/InputStreamHandler.class: \
	bin/kuro/amaktet/util/InputStreamEvent.class \
	bin/kuro/amaktet/util/InputStreamListener.class

bin/kuro/amaktet/util/InputStreamListener.class: \
	bin/kuro/amaktet/util/InputStreamEvent.class

#util.event package
bin/kuro/amaktet/util/event/CommandEvent.class:

bin/kuro/amaktet/util/event/CommandListener.class: \
	bin/kuro/amaktet/util/event/CommandEvent.class
