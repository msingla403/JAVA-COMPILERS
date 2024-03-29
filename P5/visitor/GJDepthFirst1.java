//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;

import java.util.*;
import visitor.Pair;
import java.lang.Math.*;
//import com.google.common.collect.Sets;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst1<R,A> extends GJDepthFirst<R,A>{
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	public Hashtable<String,Hashtable<String,String>> table1=new Hashtable<String, Hashtable<String,String>>();
	public Hashtable<String,Set<String>> table2= new Hashtable<String, Set<String>>();
	public Hashtable<String,Hashtable<String,String>> table3=new Hashtable<String, Hashtable<String,String>>();
	public Hashtable<String,Hashtable<String,Pair<Integer,Integer>>> table4=new Hashtable<String, Hashtable<String,Pair<Integer,Integer>>>();
	
	
	public int max_args=0;
	
	int label_no=0;
	
	int line_no;
	Hashtable<Integer,Set<String>> t1; //def
    Hashtable<Integer,Set<String>> t2; //use
    Hashtable<String,Integer> label;
    Hashtable<Integer,String> succ;
    Hashtable<Integer,Set<String>> in;
    Hashtable<Integer,Set<String>> out;
    Hashtable<Integer,Integer> next;
    Hashtable<Integer,Set<String>> live;
    Hashtable<String,Pair<Integer,Integer>> live_range;
    
    
    void set_next() {
    	 Iterator hmIterator = succ.entrySet().iterator();
   
         while (hmIterator.hasNext()) { 
             Map.Entry E = (Map.Entry)hmIterator.next(); 
             next.put((Integer)E.getKey(),label.get(E.getValue()));
         } 
    }
    
    void func(String fun_name,int args) {
    	next=new Hashtable<Integer, Integer>();
    	in=new Hashtable<Integer, Set<String>>();
    	out=new Hashtable<Integer, Set<String>>();
    	live=new Hashtable<Integer, Set<String>>();
    	
    	set_next();
    	

    	for(int i=1;i<=line_no;++i) {
    		if(t1.get(i)==null) {
    			t1.put(i,new TreeSet<String>());
    		}
    		if(t2.get(i)==null) {
    			t2.put(i,new TreeSet<String>());
    		}
    		
    		in.put(i,new TreeSet<String>());
    		out.put(i,new TreeSet<String>());
    		live.put(i,new TreeSet<String>());
    	}
    	
    	
    	
    	Set<String>temp;
    	
    	for(int j=0;j<2*line_no;++j) {
	    	for(int i=line_no-1;i>0;--i) {
	    		in.put(i,out.get(i));
	    		in.get(i).removeAll(t1.get(i));
	    		in.get(i).addAll(t2.get(i));
	    		temp=new TreeSet<String>();
	    		
	    		temp.addAll(in.get(i+1));
	    		if(next.get(i)!=null)
	    			temp.addAll(in.get(next.get(i)));
	    		out.put(i,temp);
	    	}
    	}

    	for(int i=1;i<line_no;++i) {
    		live.get(i).addAll(out.get(i));
    		live.get(i).addAll(in.get(i));
    	}
    	
    	Set<String> made_live=new HashSet<String>();
    	String temp4;
    	
    	for(int i=0;i<args;++i) {
    		live.get(1).add(i+"");
    	}
    	
    	for(int i=1;i<line_no;++i) {
    		Iterator it=live.get(i).iterator();
    		 	
    		
		        while(it.hasNext()) {
    			    temp4=(String)it.next();
    			    
    			    if(made_live.contains(temp4)==false) {
	    				int maxi=i;
	    				for(int j=i+1;j<line_no;++j) {
	    					if(live.get(j).contains(temp4)) 
	    						maxi=j;	
	    				}
	    				for(int j=i+1;j<maxi;++j) {
	    					live.get(j).add(temp4);
	    				}
    			    }
    			
		        }
    	}
    	
    	for(int i=1;i<line_no;++i) {
    		Iterator it=live.get(i).iterator();
    		while(it.hasNext()) {
    			temp4=(String)it.next();
    			
				if(live_range.get(temp4)==null)
					live_range.put(temp4,new Pair(i,i));
				else {
					live_range.get(temp4).s=i;
				}
    			
    		}
    	}
    	
    	
    	
    	Hashtable<String,String> name =new Hashtable<String, String>();
    	Set<String> spilled= new TreeSet<String>();
    	int spilter=args+18;

    	
    	Set<String> reg= new TreeSet<String>();
    	reg.addAll(Arrays.asList("t0","t1","t2","t3","t4","t5","t6","t7","s0","s1","s2","s3","s4","s5","s6","s7","t8","t9"));

    	if(args>4) {
	    	for(int i=4;i<args;++i) {
	    		name.put(i+"",(i-4)+"");
	    		spilled.add(i+"");
	    	}
    	}
    	
    	
    	String temp1,temp2,temp3;
    	Set<String>dead;
    	
    	for(int i=1;i<line_no;++i) {
    		
    		if(i>1) {
    			dead=new TreeSet<String>();
    			dead.addAll(live.get(i-1));
    			dead.removeAll(live.get(i));
    			
    			Iterator ut=dead.iterator();
    			
    			while(ut.hasNext()) {
    				temp3=(String)ut.next();
    				if(spilled.contains(temp3)==false)
    					reg.add(name.get(temp3));
    			}
    		
    		}
    		Iterator it = live.get(i).iterator(); 
            while (it.hasNext()) { 
                temp1= (String)it.next();
                if(name.get(temp1)==null) {
                	if(reg.size()>0) {
                		temp2=reg.iterator().next();
                		reg.remove(temp2);
                	}else {
                		temp2=(spilter)+"";
                		spilter++;
                		spilled.add(temp1);
                	}
                	
                	name.put(temp1,temp2);
                }
            }
            
    	}
    	
    	table1.put(fun_name,name);
    	table2.put(fun_name,spilled);

    	Iterator pt=label.entrySet().iterator();
    	Hashtable<String,String> label_name= new Hashtable<String, String>();
    	
    	while(pt.hasNext()) {
    		Map.Entry E = (Map.Entry)pt.next(); 
    		label_name.put((String)E.getKey(),"MOHIT"+label_no);
    		label_no++;
    	}
    	
    	table3.put(fun_name,label_name);
    	table4.put(fun_name,live_range);
    	
//    	System.out.println(live);
    	
    }
    
    
    boolean fl,flabel=true;
    Set<String> tempo;
	
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return (R)n.tokenImage; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      
      t1= new Hashtable<Integer, Set<String>>();
      t2=new Hashtable<Integer, Set<String>>();
      label=new Hashtable<String, Integer>();
      succ=new Hashtable<Integer, String>();
      live_range=new Hashtable<String,Pair<Integer,Integer>>();
      line_no=1;
      fl=false;

      n.f1.accept(this, argu);
      
      
      func("MAIN",0);
      
//      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n, A argu) {
      R _ret=null;
      
      t1= new Hashtable<Integer, Set<String>>();
      t2=new Hashtable<Integer, Set<String>>();
      label=new Hashtable<String, Integer>();
      succ=new Hashtable<Integer, String>();
      live_range=new Hashtable<String,Pair<Integer,Integer>>();
      line_no=1;
      fl=false;
      
      if((Integer.parseInt((String)n.f2.f0.tokenImage))>max_args){
    	  max_args=(Integer.parseInt((String)n.f2.f0.tokenImage));
      }

      
//      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      
      func((String)n.f0.f0.tokenImage,Integer.parseInt((String)n.f2.f0.tokenImage));
      
//      System.out.println(succ);
      
      
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n, A argu) {
      R _ret=null;
      flabel=false;
      n.f0.accept(this, argu);
      flabel=true;
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      Set<String> s2= new TreeSet<String>();
      s2.add((String)n.f1.f1.f0.tokenImage);
      t2.put(line_no,s2);
      
      
      succ.put(line_no,(String)n.f2.f0.tokenImage);
      
      line_no++;
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      
      succ.put(line_no,(String)n.f1.f0.tokenImage);
      line_no++;
      
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public R visit(HStoreStmt n, A argu) {
      R _ret=null;
      
      Set<String> s2= new TreeSet<String>();
      s2.add((String)n.f1.f1.f0.tokenImage);
      s2.add((String)n.f3.f1.f0.tokenImage);
      t2.put(line_no,s2);
      line_no++;
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
//      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;
      Set<String> s2= new TreeSet<String>();
      s2.add((String)n.f2.f1.f0.tokenImage);
      t2.put(line_no,s2);
      
      Set<String> s1= new TreeSet<String>();
      s1.add((String)n.f1.f1.f0.tokenImage);
      t1.put(line_no,s1);
      
      line_no++;
      
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
//      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      
      Set<String> s2= (TreeSet<String>)(n.f2.accept(this, argu));
      t2.put(line_no,s2);
      
      Set<String> s1= new TreeSet<String>();
      s1.add(((String)n.f1.f1.f0.tokenImage));
      t1.put(line_no,s1);
      
      line_no++;
      
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      
      Set<String> s2= (TreeSet<String>)(n.f1.accept(this, argu));
      t2.put(line_no,s2);
      line_no++;
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n, A argu) {
      R _ret=null;
      return n.f0.accept(this, argu);
//      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public R visit(StmtExp n, A argu) {
      R _ret=null;
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
//      n.f3.accept(this, argu);
      flabel=false;
      
      Set<String>s2=(TreeSet<String>)n.f3.accept(this, argu);
      t2.put(line_no,s2);
      line_no++;
      
      flabel=true;
      
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public R visit(Call n, A argu) {
      R _ret=null;
      
      Set<String> s2=(TreeSet<String>)n.f1.accept(this, argu);
      tempo=new TreeSet<String>();
      fl=true;
      
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
//      n.f4.accept(this, argu);
      
      fl=false;
      
      s2.addAll(tempo);
      
      return (R)s2;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=null;
      
      Set<String> s2= (TreeSet<String>)(n.f1.accept(this, argu));
      
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
      return (R)s2;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      Set<String> s2= (TreeSet<String>)(n.f2.accept(this, argu));
      s2.add(((String)n.f1.f1.f0.tokenImage));

//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
      return (R)s2;
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public R visit(Operator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n, A argu) {
      R _ret=null;
      Pair<Integer,String> p1= (Pair<Integer,String>)(n.f0.accept(this, argu));
      
      Set<String> s1=new TreeSet<String>();
      if(p1.f.equals(1)) {
    	  s1.add(p1.s);
      }
      
      return (R)s1;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n, A argu) {
      R _ret=null;
      
      if(fl)
    	  tempo.add(((String)n.f1.f0.tokenImage));
      
      Pair<Integer,String> p1=new Pair(1,(String)n.f1.f0.tokenImage);
//      n.f0.accept(this, argu);
     return (R)(p1);
//      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      Pair<Integer,String> p1=new Pair(0,(String)n.f0.tokenImage);
      return (R)p1;
//      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      String s= (String)argu;
      
      if(flabel) {
	      label.put((String)n.f0.tokenImage, line_no);
	      line_no++;
      }
      Pair<Integer,String> p1=new Pair(0,(String)n.f0.tokenImage);
      return (R)p1;
   }

}
