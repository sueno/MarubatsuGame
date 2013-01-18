import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * リフレクションを用いて動的にメソッド呼び出しを行うクラス
 * 
 * @author sueno
 * 
 */
public class CPUReflector {
	private Object refObject;
	private Class refClass;
	static private URLClassLoader classLoader;

	/**
	 * 呼び出すクラスのオブジェクトを生成
	 * 
	 * @param packageName
	 *            対象クラスのパッケージ名
	 * @param b
	 *            対象クラスの引数
	 * @param cpuclass
	 *            対象クラスのクラス名
	 * @throws Exception
	 */
	public CPUReflector(String packageName, boolean b, String cpuclass) throws Exception {
		refClass = Class.forName(packageName + "." + cpuclass, true, classLoader);
		System.out.println("LoadClass : " + refClass.getName() + "");
		Constructor cons = refClass.getConstructor(new Class[] { boolean.class });
		refObject = cons.newInstance(new Object[] { new Boolean(b) });
	}

	/**
	 * CpuPlayerクラスのnextMoveメソッド呼び出し
	 * 
	 * @param board
	 *            コンピュータの番になった時の盤の状況
	 * 
	 * @return [0] 次の手の位置を示す２次元ベクトルの行位置 [1] 次の手の位置を示す２次元ベクトルの列位置
	 */
	public int[] nextMove(int[][] board) {
		Class[] iC = new Class[] { board.getClass() };
		Object[] iO = new Object[] { board };
		System.out.println("CPUNAME:" + refObject.getClass().getName());
		try {
			Method insertMethod = refClass.getDeclaredMethod("nextMove", iC);
			Object returnValue = insertMethod.invoke(refObject, iO);
			return (int[]) returnValue;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * ロード可能なCpuPlayerクラスの一覧を取得するメソッド
	 * 
	 * @param cpuclass
	 *            CPUのクラス名(CpuPlayer)
	 * @return CpuPlayerクラス一覧
	 */
	public static ArrayList CPUClassLoader(String cpuclass) {

		ArrayList classlist = new ArrayList();

		try {
			ArrayList urlList = new ArrayList();

			URLClassLoader ucl = (URLClassLoader) ClassLoader.getSystemClassLoader();
			URL[] uclu = ucl.getURLs();
			for (int i = 0; i < uclu.length; ++i) {
				urlList.add(uclu[i]);
			}

			try {
				URL url = new URL("");
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.connect();

				BufferedInputStream bis = new BufferedInputStream(http.getInputStream());
				InputStreamReader isr = new InputStreamReader(bis);
				BufferedReader br = new BufferedReader(isr);

				String data;
				while ((data = br.readLine()) != null) {
					urlList.add(new URL(data));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			URL[] urls = new URL[urlList.size()];
			for (int i = 0; i < urlList.size(); i++) {
				urls[i] = (URL) urlList.get(i);
			}

			classLoader = URLClassLoader.newInstance(urls, ClassLoader.getSystemClassLoader());

			URL[] classPath = classLoader.getURLs();
			for (int i = 0; i < classPath.length; i++) {
				File fil = new File(classPath[i].toString());
				System.out.println("file name : " + fil);
				Pattern pt = Pattern.compile("^http.*");
				Matcher mc = pt.matcher(fil.toString());
				if (mc.find()) {
					Pattern ptt = Pattern.compile(".*\\.jar$");
					Matcher mcc = ptt.matcher(fil.getName());
					if (mcc.find()) {
						try {
							String tmp = fil.getName().replaceAll("\\.jar$", "");
							System.out.println("LoadClass : " + tmp + "." + cpuclass);
							Class cpuClass = Class.forName(tmp + "." + cpuclass, true, classLoader);

							System.out.println("くらすろーだーについかできたよ！ " + tmp);

							Class[] params = { boolean.class };
							Constructor c = cpuClass.getConstructor(params);

							classlist.add((String) tmp);
						} catch (Exception ex) {
							System.err.println("[" + fil.getName().replaceAll("\\.jar$", "") + "]ClassNotFound : Jarファイルに" + fil.getName().replaceAll("\\.jar$", "") + "." + cpuclass + "がないよ");
						}
					}
				} else {
//					File file = new File(classPath[i].toURI());
					File file = new File(new URI(classPath[i].toString()));
					String[] jars = file.list();
					if (jars != null) {
						System.out.println("length : " + jars.length);
						for (int j = 0; j < jars.length; ++j) {
							System.out.println(classPath[i] + " : " + jars[j]);
							Pattern ptt = Pattern.compile(".*\\.jar$"); // u*.jar,g*.jar
							Matcher mcc = ptt.matcher(jars[j]);
							if (mcc.find()) {
								try {
									String tmp = jars[j].replaceAll("\\.jar$", "");
									System.out.println("LoadClass : " + tmp + "." + cpuclass);
									URL[] jarurl = new URL[] { new URL(classPath[i].toString() + jars[j]) };
									urlList.add(jarurl[0]);
									System.out.println("" + jarurl[0]);
									Class cpuClass = Class.forName(tmp + "." + cpuclass, true, URLClassLoader.newInstance(jarurl, classLoader));

									Class[] params = { boolean.class };
									Constructor c = cpuClass.getConstructor(params);

									classlist.add((String) tmp);
								} catch (Exception ex) {
									System.err.println("[" + jars[j].replaceAll("\\.jar$", "") + "]ClassNotFound : Jarファイルに" + jars[j].replaceAll("\\.jar$", "") + "." + cpuclass + "がないよ");
								}
							}
						}
					}
				}
			}

			urls = new URL[urlList.size()];
			for (int i = 0; i < urlList.size(); i++) {
				urls[i] = (URL) urlList.get(i);
			}
			classLoader = URLClassLoader.newInstance(urls, ClassLoader.getSystemClassLoader());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return classlist;

	}
}
