package com.mrbt.lingmoney.admin.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 解压UTIL
 * 
 * @author Administrator
 *
 */
public final class ZipUtil {
	
	private ZipUtil() {
		
	}

	private static final int BUFFER = 2048;

	/**
	 * 解压Zip文件
	 * 
	 * @param path
	 *            文件目录
	 * @return return
	 */
	public static String unZip(String path) {
		int count = ResultNumber.MINUS_ONE.getNumber();
		String savepath = "";
		String filename = "";
		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; // 保存解压文件目录
		new File(savepath).mkdir(); // 创建保存目录
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(path, "gbk"); // 解决中文乱码问题
			Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				byte[] buf = new byte[BUFFER];

				ZipEntry entry = (ZipEntry) entries.nextElement();

				filename = entry.getName();
				boolean ismkdir = false;
				if (filename.lastIndexOf("/") != ResultNumber.MINUS_ONE
						.getNumber()) { // 检查此文件是否带有文件夹
					ismkdir = true;
				}
				filename = savepath + filename;

				if (entry.isDirectory()) { // 如果是文件夹先创建
					file = new File(filename);
					file.mkdirs();
					continue;
				}
				file = new File(filename);
				if (!file.exists()) { // 如果是目录先创建
					if (ismkdir) {
						new File(filename.substring(0,
								filename.lastIndexOf("/"))).mkdirs(); // 目录先创建
					}
				}

				file.createNewFile(); // 创建文件

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFFER);

				while ((count = is.read(buf)) > ResultNumber.MINUS_ONE
						.getNumber()) {
					bos.write(buf, 0, count);
				}
				bos.flush();
				bos.close();
				fos.close();
				is.close();
			}
			zipFile.close();
			return filename;

		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    public static void main(String[] args) {
        try {
            OutputStream os = new FileOutputStream(new File("F:/GHB_LQE_P2P_TCK_U_20180322_1.zip"));
            toZip("F:/GHB_LQE_P2P_TCK_U_20180322_1.txt", os, true);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 压缩成ZIP 方法1
     * @param srcDir 要压缩的文件夹路径 
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static File toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
            return sourceFile;
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
            throws Exception {
        byte[] buf = new byte[BUFFER];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }

                }
            }
        }
    }

}
