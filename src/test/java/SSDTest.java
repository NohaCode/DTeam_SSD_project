import static org.junit.jupiter.api.Assertions.*;

class SSDTest {

    @Test
    public void read_NandFile없는경우Read(){
        //ssd R 0
    }

    @Test
    public void read_resultFile없는경우Read(){
        //ssd R 0
    }

    @Test
    public void read_Write하지않은상태로Read(){
        //ssd R 0
    }

    @Test
    public void read_Write한주소를Read(){
        //ssd W 0 0xFFFFFFFF
        //ssd R 0
    }

    @Test
    public void read_이상한주소값Read(){
        //ssd R -1
        //ssd R 111
    }

    @Test
    public void read_같은주소여러번Read(){
        //ssd R 0
        //ssd R 0
        //ssd R 0
    }

    @Test
    public void read_다른주소연속read(){
        //ssd R 0
        //ssd R 1
        //ssd R 2
    }

    @Test
    public void read_Format못맞춘경우read(){
        //ssd R
        //ssd R 2 0xFFFFFFFF
    }

}