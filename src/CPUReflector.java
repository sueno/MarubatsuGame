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
 * ��ե쥯�������Ѥ���ưŪ�˥᥽�åɸƤӽФ���Ԥ����饹
 * 
 * @author sueno
 * 
 */
public class CPUReflector {
	private Object refObject;
	private Class refClass;
	static private URLClassLoader classLoader;

	/**
	 * �ƤӽФ����饹�Υ��֥������Ȥ�����
	 * 
	 * @param packageName
	 *            �оݥ��饹�Υѥå�����̾
	 * @param b
	 *            �оݥ��饹�ΰ���
	 * @param cpuclass
	 *            �оݥ��饹�Υ��饹̾
	 * @throws Exception
	 */
	public CPUReflector(String packageName, boolean b, String cpuclass) throws Exception {
		refClass = Class.forName(packageName + "." + cpuclass, true, classLoader);
		System.out.println("LoadClass : " + refClass.getName() + "");
		Constructor cons = refClass.getConstructor(new Class[] { boolean.class });
		refObject = cons.newInstance(new Object[] { new Boolean(b) });
	}

	/**
	 * CpuPlayer���饹��nextMove�᥽�åɸƤӽФ�
	 * 
	 * @param board
	 *            ����ԥ塼�����֤ˤʤä������פξ���
	 * 
	 * @return [0] ���μ�ΰ��֤򼨤��������٥��ȥ�ι԰��� [1] ���μ�ΰ��֤򼨤��������٥��ȥ�������
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
	 * ���ɲ�ǽ��CpuPlayer���饹�ΰ������������᥽�å�
	 * 
	 * @param cpuclass
	 *            CPU�Υ��饹̾(CpuPlayer)
	 * @return CpuPlayer���饹����
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

							System.out.println("���餹�������ˤĤ����Ǥ����衪 " + tmp);

							Class[] params = { boolean.class };
							Constructor c = cpuClass.getConstructor(params);

							classlist.add((String) tmp);
						} catch (Exception ex) {
							System.err.println("[" + fil.getName().replaceAll("\\.jar$", "") + "]ClassNotFound : Jar�ե������" + fil.getName().replaceAll("\\.jar$", "") + "." + cpuclass + "���ʤ���");
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
									System.err.println("[" + jars[j].replaceAll("\\.jar$", "") + "]ClassNotFound : Jar�ե������" + jars[j].replaceAll("\\.jar$", "") + "." + cpuclass + "���ʤ���");
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
