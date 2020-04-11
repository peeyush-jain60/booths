import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
/*
THIS PROGRAM IMPLEMENTS BOOTH'S ALGORITHM FOR MULTIPLICATION AND DIVISION OF TWO BINARY NUMBERS
THIS IS DONE AS PART OF A PROJECT BY STUDENTS OF IIITD
AMAN AGGARWAL 2018327
PEEYUSH JAIN 2018402
 */
public class booth {

    static int  firstnumber,secondnumber;
    static int bits=12;// All inputs are 12 bits long
    public static String add(String a,String b)//this functions add two binary number using each case like 0+0=0,1+0=0+1=1,1+1=0,and also minds the carry
    {
        String result=new String();
        boolean carry=false;//carry bit
        if(a.length()!=b.length())
        {System.out.println("Something Wrong");
            System.exit(0);}
        for(int i=a.length()-1;i>=0;i--)
        {
            if(a.charAt(i)=='1'&&b.charAt(i)=='1') // if 1+1
            {
                if(carry==false) //if no carry then generate carry and ans is 0
                {
                    result+=Character.toString('0');
                    carry=true;
                }
                else // if carry is there generate carry and put ans as 1
                {
                    result+=Character.toString('1');
                    carry=true;
                }
            }
            else if((a.charAt(i)=='0'&&b.charAt(i)=='1')||(a.charAt(i)=='1'&&b.charAt(i)=='0')) // if 1+0=0+1=1
            {
                if(carry==true)// if already carry then put carry=1 and ans =0
                {
                    result+=Character.toString('0');
                    carry=true;
                }
                else// if no already carry then ans=1 and carry =0
                {
                    result+=Character.toString('1');
                    carry=false;
                }
            }
            else// if 0+0
            {
                if(carry==true)
                {
                    result+=Character.toString('1');
                }
                else
                    result+=Character.toString('0');
                carry=false;

            }
        }
        /*if(carry==true)
        {
            result+=Character.toString('1');
        }*/
        String f=new String();
        for(int i=result.length()-1;i>=0;i--)//reverse the ans string
        {
            f+=Character.toString(result.charAt(i));
        }
        return f;
    }
    public static String TwoCOmplement(String a)// This function returns the two complement of binary string
    {   int found=-2;
        for(int i=a.length()-1;i>=0;i--)// find the first 1
        {
            if(a.charAt(i)=='1')
            { found=i;
                break;}
        }
        if(found==-2)
        {
            return "1"+a;
        }
        String b=new String();
        for(int i=0;i<found;i++)//reverse the rest of bits
        {
            if(a.charAt(i)=='1')
                b+=Character.toString('0');
            else{
                b+=Character.toString('1');
            }
        }
        b+=a.substring(found);
        return b;
    }
    public static String rightshift(String a,String b)// this function shifts AQq
    {
        char copy=a.charAt(0);//copy the first bit as it as in the first and second place
        String temp=Character.toString(copy)+Character.toString(copy);
        String temp2=Character.toString(a.charAt(a.length()-1));//copy the last bit of A to the first bit of Q
        for(int i=1;i<bits-1;i++)
        {
            temp+=a.charAt(i);
        }
        for(int i=0;i<bits-1;i++)
        {
            temp2+=b.charAt(i);
        }
        return temp+temp2;
    }
    public static String Multiply(String M,String Q)//This functions multiply two strings
    {
        String  Accumulator=new String();//Accumulator string
        for(int i=0;i<bits;i++)
        {
            Accumulator+='0';
        }
        char q='0';
        int times=bits;
        while(times!=0)
        {  // System.out.println(Q+ q);
            if((Q.charAt(Q.length()-1)+Character.toString(q)).compareTo("10")==0)// if 10 then do A-M
            {   String temp2=TwoCOmplement(M);
                String temp=add(Accumulator,temp2);
                Accumulator=temp;
                //System.out.println("A-M   "+Accumulator+","+Q+","+q);
            }
            else if((Q.charAt(Q.length()-1)+Character.toString(q)).compareTo("01")==0){// if 01 do A+M
                String temp = add(Accumulator, M);
                Accumulator = temp;
                // System.out.println("A+M:   "+Accumulator+","+Q+","+q);
            }
            q=Q.charAt(Q.length()-1);
            String changed=rightshift(Accumulator,Q);//Right shift
            Accumulator=changed.substring(0,bits);
            Q=changed.substring(bits);
            //System.out.println("Shift:  "+Accumulator+","+Q+","+q);
            times--;
        }
        return Accumulator+Q;

    }
    // a important function that will accept integer values and return its corresponding binary string
    public static String toBinary(int n)
    {   String str="";
    // the most common decimal to binary method is used
        while(n>0)
        {
            int bit=n%2;
            String str1;
            if(bit==0)
                str1="0";
            else
                str1="1";
            n/=2;
            str=str1+str;
        }
        if(str.equals(""))
            str="0000";
        return str;
    }
    // this function is used to subtract two binary numbers
    public static String subtract(String a,String b)
    {
        //a-b
        String aux="";
        int biglen=a.length();
        int smalllen=b.length();
        // length will be adjusted to match both the binary numbers
        if(biglen<=smalllen)
        {
            int count=smalllen-biglen;
            for(int i=0;i<=count;++i)
            {
                a="0"+a;
            }
        }
        int j=a.length()-1;
        String ans="";
        for(int i=b.length()-1;i>=0;--i,--j)
        {
            String small=Character.toString(b.charAt(i));
            String big=Character.toString(a.charAt(j));
            int bigbit=Integer.parseInt(big);
            int smallbit=Integer.parseInt(small);
            // important scenario of 0-1 is to be handled carefully while subtracting bits
            if(bigbit<smallbit)
            {
                bigbit=2;
                int aux_count=j-1;
                while(a.charAt(aux_count)!='1')
                {
                    a=a.substring(0,aux_count)+"1"+a.substring(aux_count+1);
                    aux_count--;
                }
                a=a.substring(0,aux_count)+"0"+a.substring(aux_count+1);
            }
            if(bigbit>=smallbit)
            {
                int cur_bit=bigbit-smallbit;
                if(cur_bit==0)
                    ans="0"+ans;
                else
                    ans="1"+ans;
            }
        }
        //System.out.println(ans);
        return ans;
    }
    // divide function will divide two binary numbers provided in the form of a string
    public static String[] divide(String a,String b)
    {
        String quot="";
        String rem="";
        Long divisor=Long.parseLong(b);
        int counter=0;
        String aux="0";
        while(counter<a.length())
        {   aux=aux+a.substring(counter,counter+1);
            Long auxlong=Long.parseLong(aux);
            if(divisor<=auxlong)
            {
                quot=quot+"1";
                if(divisor==auxlong)
                    aux="0";
                else
                {
                    //aux-b
                    aux=subtract(aux,b);
                }

            }
            else
            {

                quot=quot+"0";
            }
            counter++;
        }
        rem=aux;
        String[] arr=new String[2];
        arr[0]=quot;
        arr[1]=rem;
        return arr;
    }
    // function that will calculate the corresponding decimal value of the answer which is in binary format
    public static Integer toInt(String str)
    {   int ans=0;
        int counter=1;
        for(int i=str.length()-1;i>=0;--i)
        {
            if(str.charAt(i)=='1')
                ans+=counter;
            counter*=2;
        }
        return ans;
    }
    public static void main(String args[]) throws FileNotFoundException {
        Scanner in =new Scanner(System.in);
        PrintWriter out = new PrintWriter("Booth_Ans.txt");
        System.out.println("1.Multiplication\n2.Division");
        System.out.println("Enter First number:  ");
        firstnumber=in.nextInt();
        System.out.println("Enter Second number:  ");
        secondnumber=in.nextInt();
        int choice=1;
        if(choice==1)
        {   String fmultiplicand,fmultiplier;

            int multiplicand=firstnumber;
            //System.out.println(toBinary(Math.abs(multiplicand)));
            if(multiplicand<0)
            {
                int temp=Math.abs(multiplicand);
                String temp1=toBinary(temp);
                fmultiplicand=TwoCOmplement(temp1);
                //fmultiplicand=Character.toString('1')+temp1;
            }
            else {
                fmultiplicand=toBinary(multiplicand);
            }

            int multiplier=secondnumber;
            if(multiplier<0)// if negative do complement
            {
                int temp=Math.abs(multiplier);
                String temp1=toBinary(temp);
                fmultiplier=TwoCOmplement(temp1);
            }
            else {
                fmultiplier=toBinary(multiplier);
            }
            //bits=Math.max(fmultiplicand.length(),fmultiplier.length());
            System.out.println("\n Multiplication answer");
            out.println("Multiplication answer");
            System.out.print("Binary ans:  ");
            out.println("Binary ans:  ");
            if(fmultiplicand.length()<bits)
            {
                String add=new String();
                char c;
                if(multiplicand<0)
                    c='1' ;
                else
                    c='0';
                for(int i=0;i<bits-fmultiplicand.length();i++)// making them 12 bits
                {
                    add+=Character.toString(c);
                }
                fmultiplicand=add+fmultiplicand;
            }
            if(fmultiplier.length()<bits)
            {
                String add=new String();
                char c;
                if(multiplier<0)
                    c='1';
                else
                    c='0';
                for(int i=0;i<bits-fmultiplier.length();i++)
                {
                    add+=Character.toString(c);
                }
                fmultiplier=add+fmultiplier;
            }
            //System.out.println(fmultiplicand+"  "+fmultiplier);
            String ans=Multiply(fmultiplicand,fmultiplier);
            out.println(ans);
            System.out.println(ans);
            if(ans.charAt(0)=='1')
            {   int f=toInt(TwoCOmplement(ans));
                out.println("Decimal answer:  -"+f);
                System.out.println("Decimal ans: -"+f);}
            else
                {   int f=toInt(ans);
                    out.println("Decimal ans:  "+f);
                    System.out.println("Decimal ans:  "+ toInt(ans));}
            System.out.println("\n Division answer");
        }
        if(choice ==1)
        {   // this section is for division part
            // first dividend will be entered by user

            int dividend=firstnumber;
            // after dividend, user will enter divisor

            int divisor=secondnumber;
            // some base cases will be checked
            if(dividend==0)
                System.out.println(0+" "+0);
            else if(divisor==0)
                System.out.println("Divide by zero error");
            // if none of the base cases come out to be true then general division algorithm will run
            else{
                // initially it will be assumed that quotient and remainder will be positive
                boolean quot_neg=false;
                boolean rem_neg=false;
                // signs of quotient and remainder will be decided based on input
                if(dividend*divisor<0)
                    quot_neg=true;
                if(dividend<0) {
                    dividend = -dividend;
                    rem_neg=true;
                }
                if(divisor<0)
                    divisor=-divisor;
                String a=toBinary(dividend);
                String b=toBinary(divisor);
                // divide() is the main function that will compute the division result and return quotient and remainder
                String[] arr=divide(a,b);
                // System.out.println(a+" "+b);
                System.out.println("Binary ans:");
                int quot=toInt(arr[0]);
                int rem=toInt(arr[1]);
                String bit_quot=arr[0];
                String bit_rem=arr[1];
                // sign will be checked and necessary 2's complement will be calculated
                if(quot_neg)
                {
                    quot=-quot;
                    bit_quot=TwoCOmplement(bit_quot);
                }
                if(rem_neg)
                {
                    rem=-rem;
                    bit_rem=TwoCOmplement(bit_rem);
                }
                // a proper format will be followed while displaying the answers
                out.println("Division ans");
                out.println("Quotient : "+bit_quot);
                out.println("Remainder : "+bit_rem);;
                out.println(("Decimal form : "));
                out.println(("Quotient : "+quot));
                out.println(("Remainder : "+rem));
                System.out.println("Quotient : "+bit_quot);
                System.out.println("Remainder : "+bit_rem);
                System.out.println();
                System.out.println("Decimal form : ");
                System.out.println("Quotient : "+quot);
                System.out.println("Remainder : "+rem);
            }
        }
        out.close();
    }
}
