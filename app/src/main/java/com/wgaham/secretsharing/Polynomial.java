package com.wgaham.secretsharing;

/**
 * 多项式的支持类
 *
 * @author Simba
 */
public class Polynomial {

    private int[] coef; //coefficients 系数
    private int deg;//指数，次数 degree of polynomial (0 for the zero polynomial)

    //a*x^b 多项式中的通式
    Polynomial(int a, int b) {
        coef = new int[b + 1];
        coef[b] = a;  //a为系数
        deg = degree();
    }

    // return the degree of this polynomial (0 for the zero polynomial)
    int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i] != 0)
                d = i;
        return d;
    }

    //return c = a+b
    Polynomial plus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, Math.max(a.deg, b.deg));
        for (int i = 0; i <= a.deg; i++) {
            c.coef[i] += a.coef[i];
        }
        for (int i = 0; i <= b.deg; i++) {
            c.coef[i] += b.coef[i];
        }
        c.deg = c.degree();
        return c;
    }


    // 求导
    Polynomial differentiate(int d) {
        if (deg == 0) return new Polynomial(0, 0);
        Polynomial deriv = new Polynomial(0, deg - 1);
        deriv.deg = deg - 1;
        for (int j = 0; j < d; j++) {
            for (int i = 0; i < deg; i++)
                deriv.coef[i] = (i + 1) * coef[i + 1];
        }
        return deriv;
    }

    //返回求导后的系数*自变量的值
    public String polynomial1D(Polynomial polynomial, int x) {
        int result = 0;
        polynomial.coef = coef;
        for (int i = coef.length - 1; i >= 0; i--) {
            result = result * x + coef[i];
        }
        String resultString = String.valueOf(result);
        return resultString;
    }

    int polynomial1D1(Polynomial polynomial, int x) {
        int result = 0;
        polynomial.coef = coef;
        for (int i = coef.length - 1; i >= 0; i--) {
            result = result * x + coef[i];
        }

        return result;
    }

    //输出重构的函数的系数矩阵，在此方法中b代表了指数，x代表了选中的重构人员的变量，例如：x=2，则在计算中，x为2
    //polynomial为要带入计算的多项式，若等级为0，带入原函数，等级为1，带入一阶求导的，等级为2，带入二阶求导的，以此类推
    //传入的e是等级值，因为下面要用到l,l代表了原始多项式的长度，可以用等级来表示，所以又定义了一个形参，把相对应的L值传过来
    int cal(Polynomial polynomial, int b, int x, int e) {
        int result = 0;
        //假设存在一个初始多项式系数的数组长度l
        //l为3只是适用于三阶，即三个等级的函数，若为变化的，则需要用L（即等级数），但还未想到如何传入
        int[] coef = polynomial.coef;
        int w = e - coef.length;
        if (b < w) {
            result = 0;
        } else if (b - w < coef.length) {
            result = (int) (coef[b - w] * Math.pow(x, b - w));
        }
        return result;
    }

    public String toString() {
        if (deg == 0)
            return "" + coef[0];
        if (deg == 1)
            return coef[1] + "x + " + coef[0];

        StringBuilder s = new StringBuilder(coef[deg] + "x^" + deg);
        for (int i = deg - 1; i >= 0; i--) {
            if (coef[i] == 0)
                continue;
            else if (coef[i] > 0)
                s.append(" + ").append(coef[i]);

            else if (coef[i] < 0)
                s.append(" - ").append(-coef[i]);
            if (i == 1)
                s.append("x");
            else if (i > 1)
                s.append("x^").append(i);
        }
        //return PolynomialPrint.newPrint(s);
        return s.toString();
    }


}