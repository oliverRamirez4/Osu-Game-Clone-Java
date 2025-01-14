import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class MusicPlayer extends Thread{
	
	private File audio;

    private AudioInputStream ais;
    private AudioFormat format;
    private DataLine dl;

    private boolean isPlaying;
    private boolean isPause;
    private boolean isComplete;
    
    private int volumeInt = 20;
    private FloatControl volumControl;


    public MusicPlayer(File audio) {
    		this.audio = audio;
    		this.decodeToPCMSigned();
    		this.loadMusicToClip();
    }
    
    private void decodeToPCMSigned(){

        AudioInputStream origAis = null;
        AudioFormat origFormat = null;
    
        try {
            //open file in stream
            origAis = AudioSystem.getAudioInputStream(audio);
            if (origAis != null){
                //get the file orignal format
                origFormat = origAis.getFormat();

                if (origFormat.getEncoding() == AudioFormat.Encoding.PCM_SIGNED){
                    ais = origAis;
                    format = origFormat;
                    return;
                }

                //decode the orignal format to pcm
                format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                                                origFormat.getSampleRate(), 
                                                16, 
                                                origFormat.getChannels(), 
                                                origFormat.getChannels() * 2, 
                                                origFormat.getSampleRate(), 
                                                false);
                //get the decoded stream
                ais = AudioSystem.getAudioInputStream(format, origAis); 
            }
            else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void loadMusicToClip(){
        if (ais == null || format == null){
            System.out.println("null ais or format");
            return;
        }
        DataLine.Info info = new DataLine.Info(Clip.class, format, ((int)ais.getFrameLength() * format.getFrameSize()));
        try {
            Clip clip = (Clip) AudioSystem.getLine(info);
            if (clip != null){
                this.dl = clip;
                dl.addLineListener((LineListener) new LineListener() {
                    
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType().equals(LineEvent.Type.STOP)){
                            if (isPlaying && !isPause){
                                isComplete = true;
                                clip.setMicrosecondPosition(0);
                            }
                        }
                    }
                    
                });
                clip.open(ais);
                adjustVolume();
                readyPlaying();
            }
            else{
                System.out.println("null clip");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void adjustVolume(){
        if (dl != null && dl.isControlSupported(FloatControl.Type.MASTER_GAIN)){
            volumControl = (FloatControl) dl.getControl(FloatControl.Type.MASTER_GAIN);
            volumControl.setValue((float)Math.log10(volumeInt / 100.0 * 2 - 0.1) * 20);
        }
    }
    
    private void readyPlaying(){
        isPlaying = true;
    }

    private void stopPlaying(){
        pauseMusic();

        isPlaying = false;
        if (dl != null){
            dl.drain();
            dl.close();
        }

        try {
            if (ais != null){
                ais.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void resumeMusic(){
        if (!isPlaying){
            return;
        }
        if (isComplete){
            isComplete = false;
        }
        if (isPause){
            isPause = false;
        }
        if (dl != null && !dl.isRunning()){
            dl.start();
        }
    }
    
    public void pauseMusic(){
        if (!isPlaying){
            return;
        }
        if (!isPause){
            isPause = true;
        }
        if (dl != null && dl.isRunning()){
            dl.stop();
        }
    }
    
    public long getPlayedMillis(){
        if (audio == null || dl == null || isComplete){
            return 0l;
        }
        return Math.min(dl.getMicrosecondPosition() / 1000, getDuration());
    }

    public long getDuration(){
        if (audio == null){
            return 0l;
        }
        if (dl == null || !(dl instanceof Clip)){
            return 0l;
        }
        return ((Clip)dl).getMicrosecondLength() / 1000;
    }
    
    public int getVolume(){
        return volumeInt;
    }

    public void setVolume(int v){
        volumeInt = v;
        adjustVolume();
    }
    
    public boolean isComplete() {
    		return isComplete;
    }

    
    public static void main(String[] args) {
    		MusicPlayer m = new MusicPlayer(new File("Songs\\470977 Mili - worldexecute(me);\\audio.mp3"));
    		System.out.println(m.getDuration());
    		new Thread() {
    			@Override
    			public void run() {
    				try {
					Thread.sleep(1000);
    					m.resumeMusic();
//					System.out.println(m.getPlayedMillis());
//					Thread.sleep(3000);
//					System.out.println(m.getPlayedMillis());
//					Thread.sleep(1000);
//					System.out.println(m.getPlayedMillis());
//					m.pauseMusic();
//					Thread.sleep(5000);
//					m.resumeMusic();
//					System.out.println(m.getPlayedMillis());
//					Thread.sleep(1000);
//					System.out.println(m.getPlayedMillis());
					while(true) {}
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    			}
    		}.start();
    }
    
}
