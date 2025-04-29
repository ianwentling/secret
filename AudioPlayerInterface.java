public interface AudioPlayerInterface {

        void play(String audioFilePath);
        void stop();
        void pause();
        void resume();

        //this is all available to be created in ANY class, refer to how its setup in dodgyduck and just copy that
}


