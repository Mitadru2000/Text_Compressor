//File compressor
import java.util.Scanner;
class compressor
{
    Tree finalTree=null;
    String seq="",original="",binaryseq="";
    int originalsize=0,compressedsize=0;
    codes cd=new codes();
    class word
    {
        wordNode start;
        class wordNode
        {
            String alphabet;
            int count;
            wordNode next;
            wordNode(String c)
            {
                alphabet=c;
                count=1;
                next=null;
            }
        }
        void insert(String c)
        {
            if(start==null)
            {
                start=new wordNode(c);
            }
            else
            {
                wordNode x=start;
                while(x.next!=null)
                {
                    x=x.next;
                }
                x.next=new wordNode(c);
            }
        }

        void increment(String c)
        {
            wordNode x=start;
            while((x.alphabet).equals(c)==false)
            {
                x=x.next;
            }
            x.count++;
        }
    }

    void frequency(String s)
    {
        String c;
        original=s;
        int i,j;
        word wd=new word();
        for(i=0;i<s.length();i++)
        {
            j=0;
            c=s.substring(i,i+1);
            if(wd.start==null)
            {
                wd.insert(c);
            }
            else
            {
                word.wordNode x=wd.start;
                while(x!=null)
                {
                    if((x.alphabet).equals(c)==true)
                    {
                        wd.increment(c);
                        j=1;
                        break;
                    }
                    x=x.next;
                }
                if(j==0)
                {
                    wd.insert(c);
                }
            }
        }
        word.wordNode x=wd.start;
        while(x!=null)
        {
            System.out.println(x.alphabet+" "+x.count);
            x=x.next;
        }
        minheap mh=new minheap();

        x=wd.start;
        while(x!=null)
        {
            mh.put(x.alphabet,x.count);
            x=x.next;
        }
        System.out.println("Heap:- ");
        mh.display();
        converter(mh);
    }

    void converter(minheap mh)
    {
        listOfTree list=new listOfTree();
        listOfTree.listNode ls=null;
        int j1,j2;
        Tree top=null,l=null,r=null;
        while(mh.head!=null)
        {
            j1=0;j2=0;
            minheap.Node x=mh.get();
            if(mh.head==null)
            {
                break;
            }
            minheap.Node y=mh.get();            
            System.out.println("Inserted "+(x.count+y.count));
            mh.put((x.alphabet+y.alphabet),(x.count+y.count));
            mh.display();
            System.out.println();
            System.out.println("Tree -"); 
            ls=list.listtop;
            while(ls!=null)
            {
                if(ls.tr.alphabet.equals(x.alphabet)==true)
                {
                    j1=1;
                    l=ls.tr;
                }
                if(ls.tr.alphabet.equals(y.alphabet)==true)
                {
                    j2=1;
                    r=ls.tr;
                }
                ls=ls.next;
            }
            top=new Tree(x.alphabet+y.alphabet,x.count+y.count);
            if(j1==0&&j2==0)
            {                
                top.left=new Tree(x.alphabet,x.count); 
                top.right=new Tree(y.alphabet,y.count); 
                list.add(top);
            }
            else if(j1==0&&j2==1)
            {
                top.left=new Tree(x.alphabet,x.count); 
                top.right=r;
                list.add(top);
            }
            else if(j1==1&&j2==0)
            {
                top.left=l;
                top.right=new Tree(y.alphabet,y.count);
                list.add(top);
            }
            else
            {
                top.left=l;
                top.right=r;
                list.add(top);
            }
            System.out.println();
            displaylist(list.listtop);
            
            //System.out.println(j1+"  "+j2);
        }
        //displaytree(finalTree);
        extract(finalTree);
        showcodes();
    }
    void extract(Tree x)
    {        
        //System.out.println("Inside extract");
        while(x!=null)
        {
            if(x.left!=null)
            {
                seq+="0";
                //System.out.println(x.alphabet);
                extract(x.left);
            }
            if(x.left==null && x.right==null)
            {
                cd.insert(x.alphabet,seq);
                //System.out.println(x.alphabet+" "+seq);
                seq=(seq.length()<=1)?"":seq.substring(0,seq.length()-1);
                return;                
            }
            if(x.right!=null)
            {
                seq+="1";
                //System.out.println(x.alphabet);
                extract(x.right);
            }
            //System.out.println(x.alphabet);
            seq=(seq.length()<=1)?"":seq.substring(0,seq.length()-1);
            break;
        }
    }
    void showcodes()
    {
        codes.codeNode x=cd.begin;
        int i;
        compressedsize=0;
        originalsize=original.length()*8;
        while(x!=null)
        {
            System.out.println(x.alphabet+" "+x.binary);
            compressedsize+=8+(x.binary).length();
            x=x.next;
        }
        //x=cd.begin;
        System.out.println(compressedsize);
        System.out.println("The binary encoded text =");
        for(i=0;i<original.length();i++)
        {
            x=cd.begin;
            while(x!=null)
            {
                if(x.alphabet.equals(original.substring(i,i+1))==true)
                {
                    binaryseq+=x.binary;
                    break;
                }
                x=x.next;
            }
        }
        System.out.println(binaryseq);
        compressedsize+=binaryseq.length();
        System.out.println("The original size of the text is = "+originalsize+" bits.");
        System.out.println("The compressed size of the text is ="+compressedsize+" bits.");
    }
    class codes
    {
        class codeNode
        {
            String alphabet;
            String binary;
            codeNode next;
            codeNode(String s1,String s2)
            {
                this.alphabet=s1;
                this.binary=s2;
                this.next=null;
            }
        }
        codeNode begin=null;
        void insert(String s1,String s2)
        {
            if(begin==null)
            {
                begin=new codeNode(s1,s2);
            }
            else
            {
                codeNode x=begin;
                while(x.next!=null)
                {
                    x=x.next;
                }
                x.next=new codeNode(s1,s2);
            }
        }
    }
    class minheap
    {
        class Node
        {
            String alphabet;
            int count;
            Node next; 
            Node(String c,int d)
            {
                this.alphabet=c;
                this.count=d;
                this.next=null;
            }
        }
        Node head=null;
        int pointer=0;
        void put(String c,int d)
        {
            if(head==null)
            {
                head=new Node(c,d);
                pointer++;
            }
            else
            {
                Node x=head;
                while(x.next!=null)
                {
                    x=x.next;
                }
                x.next=new Node(c,d);
                pointer++;
            }
            swim();
        }

        void swim()
        {
            Node x,y;
            int k=pointer,i;

            while(k>1)
            {
                x=head;
                y=head;
                i=1;
                while(i<k)
                {
                    x=x.next;
                    i++;
                }
                i=1;
                k=k/2;
                while(i<k)
                {
                    y=y.next;
                    i++;
                }
                if(x.count<y.count)
                {
                    swap(x,y);
                }
            }
        }

        Node get()
        {
            System.out.println("Received "+head.alphabet+" "+head.count); 
            Node received=new Node(head.alphabet,head.count);
            //received.next=null;
            pointer--;
            Node x=head;
            if(head.next==null)
            {
                head=null;
            }
            else
            {
                while(x!=null&&x.next!=null)
                {
                    if(x.next.next==null)
                    {
                        swap(head,x.next);
                        x.next=null;
                    }
                    x=x.next;
                }           
                sink();
            }
            display();
            System.out.println();
            return received;
        }

        void sink()
        {
            Node x,y;
            int k=1,i,m,n;
            while(2*k+1<=pointer||2*k<=pointer)
            {
                x=head;
                y=head;
                i=1;
                while(i<k)
                {
                    x=x.next;
                    i++;
                }
                i=1;
                if(2*k+1<=pointer)
                {
                    m=2*k+1;
                    n=2*k;
                    k=minchild(m,n);
                }
                else
                {
                    k=2*k;
                }                
                while(i<k)
                {
                    y=y.next;
                    i++;
                }
                if(y.count<x.count)
                {
                    swap(x,y);
                }
            }
        }

        int minchild(int m,int n)
        {
            Node x=head,y=head;
            int i=1;

            while(i<m)
            {
                x=x.next;
                i++;
            }
            i=1;
            while(i<n)
            {
                y=y.next;
                i++;
            }
            return (x.count<y.count)?m:n; 
        }

        void display()
        {
            Node x=head;
            while(x!=null)
            {
                System.out.println(x.alphabet+" "+x.count);
                x=x.next;
            }
        }

        void swap(Node x,Node y)
        {
            int tempc;
            String tempa;
            tempa=x.alphabet;
            tempc=x.count;
            x.alphabet=y.alphabet;
            x.count=y.count;
            y.alphabet=tempa;
            y.count=tempc;
        }
    }

    class Tree
    {
        int count;
        String alphabet;
        Tree left;
        Tree right;
        Tree(String s,int d)
        {
            this.alphabet=s;
            this.count=d;
            this.left=null;
            this.right=null;
        }
    }

    class listOfTree
    {
        class listNode
        {
            Tree tr;
            listNode next;
            listNode(Tree x)
            {
                this.tr=x;
                this.next=null;
            }
        }        
        listNode listtop=null;
        void add(Tree x)
        {            
            if(listtop==null)
            {
                listtop=new listNode(x);
            }
            else
            {
                listNode y=listtop;
                while(y.next!=null)
                {
                    y=y.next;
                }
                y.next=new listNode(x);
            }                
        }
    }
    void displaylist(listOfTree.listNode x)
    {
        listOfTree.listNode y=x;
        if(x==null)
        {
            System.out.println("empty");
        }
        else
        {
            while(y.next!=null)
            {
                y=y.next;
            }
            finalTree=y.tr;
            displaytree(y.tr);
        }
    }

    void displaytree(Tree x)
    {
        while(x!=null)
        {
            displaytree(x.left);
            System.out.println(x.alphabet+" "+x.count);
            displaytree(x.right);
            break;
        }
    }
    //Tree top=null;
    //treeNode l=null,r=null;

    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        compressor z=new compressor();
        compressor.minheap mh=z.new minheap();
        int ch,d;
        char c;
        String s;
        System.out.println("1.Word Frequency 2.Heap");
        ch=sc.nextInt();
        switch(ch)
        {
            case 1:
            System.out.println("Enter text.");
            String st=sc.next();
            st+=sc.nextLine();
            z.frequency(st);
            break;
            case 2:
            do
            {
                System.out.println("1.Put 2.Get 3.Display");
                ch=sc.nextInt();
                switch(ch)
                {
                    case 1:
                    System.out.println("Enter an alphabet and its count");
                    s=sc.next();
                    d=sc.nextInt();                    
                    mh.put(s,d);
                    break;
                    case 2:
                    mh.get();
                    break;
                    case 3:
                    mh.display();
                }
                System.out.println("Continue?[y/n]");
                c=sc.next().charAt(0);
            }
            while(c=='y'||c=='Y');
        }
    }
}