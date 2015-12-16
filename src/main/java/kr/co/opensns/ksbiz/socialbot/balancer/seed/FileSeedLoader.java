package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Local File에서 Seed정보를 불러오기 위한 Loadable 추상 클래스의 구현체
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 28., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 28., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class FileSeedLoader extends Loadable{
	
	Logger logger;
	
	public FileSeedLoader(){
		logger = Logger.getLogger(this.getClass());
	}
	
	@Override
	public SeedQueue Load(String path, String type) {
		SeedQueue q = new SeedQueue(00);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
			logger.info("Reading Seed file : "+path);
			String line;
			while((line=br.readLine())!=null){
				String[] csv = line.split(",");
				SeedEntity seed = new SeedEntity();
				
				if(csv.length < 6) continue;
				
				seed.setSiteId(Integer.parseInt(csv[1]));
				seed.setSeedId(csv[2]);
				seed.setCursor(csv[3]);
				seed.setVisitCnt(csv[4].equals("null")?0:Integer.parseInt(csv[4]));
				seed.setDocCount(csv[5].equals("null")?0:Integer.parseInt(csv[5]));
				seed.setLastVisitDate(new Date());
				
				q.put(seed);
				logger.info("Seed : "+seed.getSeedId());
			}
			br.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		} finally{
		
		}
		
		return q;
	}
}
