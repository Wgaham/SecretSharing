package com.wgaham.secretsharing;

/**
 * 构建两个方法
 * 1.接受一个秘密值（int m），返回一个子份额序列（一维整形数组）
 * 2.接受参数（坐标序列（a,b），子份额序列[]）,返回秘密值
 *
 * @author Simba
 */

public class Convey {
    //1.接受传递的秘密值m，等级数L,参与者n
    int[] Receivingserect(int m, int L, int n, String[] arrStrings, int[] t) throws Exception {
        int arr[] = new int[n];//用于接收返回的子份额序列

        //构建多项式
        Polynomial p = new Polynomial(m, 0);
        //以字符数组的形式接收多项式的系数和指数，
        if (arrStrings.length % 2 == 0) {
            for (int i = 0; i < arrStrings.length; i = i + 2) {
                if ("0".equals(arrStrings[i + 1])) {
                    int a = Integer.parseInt(arrStrings[i]);
                    int b = Integer.parseInt(arrStrings[i + 1]);
                    Polynomial p0 = new Polynomial(a, b);
                    p = p.plus(p0);
                } else {
                    throw new Exception("秘密是作为常数项存在的，在输入时请不要再次输入常数项");
                }
            }
        } else {
            throw new Exception("多项式系数与指数匹配不当");
        }

        //需传入 等级阈值用数组t接收
        //根据传入的等级求出相对应的求导过后的多项式
        //子份额分发
        if (t.length <= L) {
            Polynomial p1 = p;
            int s = t[0] - 1;
            for (int i = 0; i < L; i++) {
                if (i == 0) {
                    Polynomial p2 = p1;
                    for (int j = 1; j <= t[i]; j++) {
                        arr[j - 1] = p2.polynomial1D1(p2, j);  //L0等级的子份额存储
                    }
                } else {
                    Polynomial p2 = p1.differentiate(i);//按照等级求导
                    p1 = p2;
                    for (int j = 1; j <= t[i]; j++) {
                        arr[s + j] = p2.polynomial1D1(p2, j);
                        if (j == t[i]) {
                            s += t[i];
                        }
                    }
                }
            }
        } else {
            throw new Exception("传递的阈值数组t等级超过了L");
        }
        return arr;
    }

    //2.接受参数（坐标序列（a,b），子份额序列[]）,返回秘密值
    double Receivingsequence(int[] arrStrings, int[] br, int L) throws Exception {
        double a = 0;

        //1.选择重构者
        if (arrStrings.length % 2 == 0) {
            //2.构造新函数
            Polynomial p4 = new Polynomial(1, 0);
            for (int i = 1; i < L; i++) {
                Polynomial p5 = new Polynomial(1, i);
                p4 = p4.plus(p5);
            }

            Matrix matrix = new Matrix();
            double[][] as = new double[L][L];//数组as[][]存放原始的系数

            //将系数存入as[][]中
            for (int i = 0; i < arrStrings.length / 2; i++) {
                if (i == 0) {
                    for (int j = 0; j < L; j++) {
                        Polynomial p5 = p4;
                        as[i][j] = p5.cal(p5, i, arrStrings[2 * i], L);
                    }
                }
                //按照等级求导
                else {
                    Polynomial p6 = p4.differentiate(i);//按照等级求导
                    p4 = p6;
                    for (int j = 0; j < L; j++) {
                        as[i][j] = p4.cal(p6, j, arrStrings[2 * i], L);
                    }
                }

            }
            //5.求输出矩阵X的行列式的值,//v代表了原行列式的值
            double v = matrix.determinant(as);

            //依次替换X中的列，生成新的行列式，计算系数,先复制到一个数组A中，再进行替换
            double A[][] = new double[L][L];//长度怎么解决？长度应该是as[][]数组的长度，因为要复制数组到A中
            //  double y[]=new double[3];//存放行列式的值


            for (int i = 0; i < L; i++) {
                System.arraycopy(as[i], 0, A[i], 0, L);
            }

            //替换列，以等级作为切入点
            //  for(int i=0;i<L;i++) {
            double[][] B = new double[A.length][A[0].length];
            B[0][0] = A[0][0];
            //复制数组A给数组B，用来替换数组B中某一列的值
            for (int k = 0; k < A.length; k++) {
                System.arraycopy(A[k], 0, B[k], 0, A.length);
            }

            for (int j = 0; j < A.length; j++) {
                B[j][0] = br[j];

            }

            //计算数组B的行列式值
            double u = matrix.determinant(B);
            //重构出秘密
            a = u / v;
        } else {
            throw new Exception("传递的重构者坐标数据有误，请检查");
        }
        return a;
    }

}
