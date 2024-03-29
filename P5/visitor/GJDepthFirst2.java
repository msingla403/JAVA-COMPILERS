//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.lang.Math.*;

import java.util.*;
/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst2<R,A> extends GJDepthFirst<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	public Hashtable<String,Hashtable<String,String>> table1;
	public Hashtable<String,Set<String>> table2;
	public Hashtable<String,Hashtable<String,String>> table3;
	public Hashtable<String,Hashtable<String,Pair<Integer,Integer>>> table4;
	
	
	Hashtable<String,String>name;
	Set<String>spilled;
	Hashtable<String,String>label;
	Hashtable<String,Pair<Integer,Integer>>live_range;
	int args,line_no;
	boolean simplfl=false,tempo=false,flabel=true;
	Vector<String>fun_arg;
	
	public int max_args;
	
	
	
   public R visit(NodeList n, A argu) {
      R _ret=null;
      String ans="";
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         ans=ans+(String)e.nextElement().accept(this,argu);
         _count++;
      }
      return (R)ans;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         String ans="";
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            ans=ans+(String)e.nextElement().accept(this,argu);
            _count++;
         }
         return (R)ans;
      }
      else
         return (R)("");
   }

   public R visit(NodeOptional n, A argu) {
	   String ans;
      if ( n.present() )
         ans=label.get((String) n.node.accept(this,argu));
      else
         ans="";
      return (R)(ans+" \n");
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      String ans="";
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         ans=ans+(String)e.nextElement().accept(this,argu);
         _count++;
      }
      return (R)ans;
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
      
      name=table1.get("MAIN");
      spilled=table2.get("MAIN");
      label=table3.get("MAIN");
      live_range=table4.get("MAIN");
      args=0;
      line_no=0;

//      System.out.println(name);
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
      String ans="MAIN [ "+args+" ] [ "+(max_args+100)+" ] [ "+(max_args+5)+" ]\n"+(String) n.f1.accept(this, argu)+"END\n";
      if(spilled.size()>0)
    	  ans=ans+"//SPILLED\n";
      else
    	  ans=ans+"//NOTSPILLED\n";
      
      System.out.println(ans);
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
      return n.f0.accept(this, argu);
//      return _ret;
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
      name=table1.get((String)n.f0.f0.tokenImage);
      spilled=table2.get((String)n.f0.f0.tokenImage);
      args=Integer.parseInt((String)n.f2.f0.tokenImage);
      label=table3.get((String)n.f0.f0.tokenImage);
      live_range=table4.get((String)n.f0.f0.tokenImage);
      line_no=0;
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
//      n.f3.accept(this, argu);
      String ans=(String)n.f0.f0.tokenImage+" [ "+args+" ] [ "+(max_args+100)+" ] [ "+(max_args+5)+" ]\n"+
    		    
    		  	(String)n.f4.accept(this, argu);
      if(spilled.size()>0)
    	  ans=ans+"//SPILLED\n";
      else
    	  ans=ans+"//NOTSPILLED\n";

      System.out.println(ans);
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
      String ans;
      flabel=false;
      ans=(String)n.f0.accept(this, argu);
      flabel=true;
      return (R)ans;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return (R)("NOOP\n");
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return (R)"ERROR\n";
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      line_no++;
      String t1,ans;
      t1=(String)n.f1.accept(this, argu);
      
      if(spilled.contains(t1)) 
		  ans="ALOAD v0 SPILLEDARG "+name.get(t1)+"\n";
      else 
    	  ans="MOVE v0 "+name.get(t1)+"\n";  
      
      ans=ans+"CJUMP v0 "+label.get((String)n.f2.f0.tokenImage)+"\n";
      
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
//      n.f2.accept(this, argu);
      return (R)ans;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      line_no++;
      String a1,ans;
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
      a1=(String)n.f1.f0.tokenImage;
      ans="JUMP "+label.get(a1)+"\n";
      return (R)ans;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public R visit(HStoreStmt n, A argu) {
      String t1,ans="",t2;
      line_no++;
//	      n.f0.accept(this, argu);
      t1=(String)n.f1.accept(this, argu);
      t2=(String)n.f3.accept(this, argu);
//	      n.f3.accept(this, argu);
//      ans="\\\\HSTORE\n";
      
      if(spilled.contains(t1)) 
		  ans=ans+"ALOAD v0 SPILLEDARG "+name.get(t1)+"\n";
      else 
    	  ans=ans+"MOVE v0 "+name.get(t1)+"\n";  
      
      if(spilled.contains(t2)) 
		  ans=ans+"ALOAD v1 SPILLEDARG "+name.get(t2)+"\n";
      else 
    	  ans=ans+"MOVE v1 "+name.get(t2)+"\n";  
      
      ans=ans+"HSTORE v0 "+(String)n.f2.f0.tokenImage+" v1\n";
      
      
      return (R)ans;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;
      line_no++;
      String t1,ans="",t2;

      t1=(String)n.f1.accept(this, argu);
      t2=(String)n.f2.accept(this, argu);

      
      if(name.get(t1)!=null) {
    	  
    	  Pair<Integer,Integer>p1=live_range.get(t1);
    	  
    	  if(p1.f>line_no || p1.s<line_no)
    		  return (R)("");
    	  
	      if(spilled.contains(t2)) 
			  ans="ALOAD v1 SPILLEDARG "+name.get(t2)+"\n";
	      else 
	    	  ans="MOVE v1 "+name.get(t2)+"\n";  
	      
	      ans=ans+"HLOAD v0 v1 "+(String)n.f3.f0.tokenImage+"\n";
	      
	      if(spilled.contains(t1)) 
			  ans=ans+"ASTORE SPILLEDARG "+name.get(t1)+" v0\n";
		  else
			  ans=ans+"MOVE "+name.get(t1)+" v0\n";
      }
      
      return (R)ans;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      line_no++;
      String t1="",ans,temp;
//      n.f0.accept(this, argu);
      temp=(String)n.f1.accept(this, argu);
      ans=(String)n.f2.accept(this, argu);
      
      if(name.get(temp)!=null) {
    	  Pair <Integer,Integer>p1=live_range.get(temp);
          
          if(p1.f<=line_no && p1.s>=line_no) {
		      if(spilled.contains(temp)) 
				  t1="ASTORE SPILLEDARG "+name.get(temp)+" v0\n";
			  else
				  t1="MOVE "+name.get(temp)+" v0\n";
          }
      }
      ans=ans+t1;
      
      
      return (R)ans;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      line_no++;
      String a1,ans;
//      n.f0.accept(this, argu);
      a1=(String)n.f1.accept(this, argu);
      ans=a1+"PRINT v0\n";
      return (R)ans;
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
      
      String ans="ASTORE SPILLEDARG "+(args+0)+" s0\n"+
	 			 "ASTORE SPILLEDARG "+(args+1)+" s1\n"+
	 			 "ASTORE SPILLEDARG "+(args+2)+" s2\n"+
	 			 "ASTORE SPILLEDARG "+(args+3)+" s3\n"+
	 			 "ASTORE SPILLEDARG "+(args+4)+" s4\n"+
	 			 "ASTORE SPILLEDARG "+(args+5)+" s5\n"+
	 			 "ASTORE SPILLEDARG "+(args+6)+" s6\n"+
	 			 "ASTORE SPILLEDARG "+(args+7)+" s7\n";
      
      for(int i=0;i< args && i<4;++i) {
    	  ans=ans+"MOVE s"+i+" a"+i+"\n";
      }
      
	ans=ans+(String)n.f1.accept(this, argu)+(String)n.f3.accept(this, argu);
//      n.f0.accept(this, argu);
//      
//      n.f2.accept(this, argu);
//      n.f4.accept(this, argu);
      
      
      
      ans= ans+  "ALOAD s0 SPILLEDARG "+(args+0)+"\n"+
    			 "ALOAD s1 SPILLEDARG "+(args+1)+"\n"+
    			 "ALOAD s2 SPILLEDARG "+(args+2)+"\n"+
    			 "ALOAD s3 SPILLEDARG "+(args+3)+"\n"+
    			 "ALOAD s4 SPILLEDARG "+(args+4)+"\n"+
    			 "ALOAD s5 SPILLEDARG "+(args+5)+"\n"+
    			 "ALOAD s6 SPILLEDARG "+(args+6)+"\n"+
    			 "ALOAD s7 SPILLEDARG "+(args+7)+"\nEND\n";
      return (R)ans;
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
      String ans,a1;
//      n.f0.accept(this, argu);
      a1=(String)n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      tempo=true;
      fun_arg=new Vector<String>();
      n.f3.accept(this, argu);
      tempo=false;
      
      ans=a1+"ASTORE SPILLEDARG "+(args+8)+" t0\n"+
		 "ASTORE SPILLEDARG "+(args+9) +" t1\n"+
		 "ASTORE SPILLEDARG "+(args+10)+" t2\n"+
		 "ASTORE SPILLEDARG "+(args+11)+" t3\n"+
		 "ASTORE SPILLEDARG "+(args+12)+" t4\n"+
		 "ASTORE SPILLEDARG "+(args+13)+" t5\n"+
		 "ASTORE SPILLEDARG "+(args+14)+" t6\n"+
		 "ASTORE SPILLEDARG "+(args+15)+" t7\n"+
		 "ASTORE SPILLEDARG "+(args+16)+" t8\n"+
		 "ASTORE SPILLEDARG "+(args+17)+" t9\n";
      
      Iterator it=fun_arg.iterator();
      
      int arg_count=0;
      String temp,t1,t2;
      
      while(it.hasNext()) {
    	  temp=(String)it.next();
    	  if(spilled.contains(temp)) 
    		  t1="ALOAD v1 SPILLEDARG "+name.get(temp)+"\n";
    	  else
    		  t1="MOVE v1 "+name.get(temp)+"\n";
    	  
    	  if(arg_count<4) {
    		  ans=ans+t1+"MOVE a"+arg_count+" v1\n";
    	  }
    	  else {
    		  ans=ans+t1+"PASSARG "+(arg_count-3)+" v1\n";
    	  }
    	  arg_count++;
      }
      
      ans=ans+  "CALL v0\n"+ 
    		     "ALOAD t0 SPILLEDARG "+(args+8)+"\n"+
				 "ALOAD t1 SPILLEDARG "+(args+9)+"\n"+
				 "ALOAD t2 SPILLEDARG "+(args+10)+"\n"+
				 "ALOAD t3 SPILLEDARG "+(args+11)+"\n"+
				 "ALOAD t4 SPILLEDARG "+(args+12)+"\n"+
				 "ALOAD t5 SPILLEDARG "+(args+13)+"\n"+
				 "ALOAD t6 SPILLEDARG "+(args+14)+"\n"+
				 "ALOAD t7 SPILLEDARG "+(args+15)+"\n"+
				 "ALOAD t8 SPILLEDARG "+(args+16)+"\n"+
				 "ALOAD t9 SPILLEDARG "+(args+17)+"\n";
      
//      n.f4.accept(this, argu);
      return (R)ans;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=null;
      String a1,ans;
//      n.f0.accept(this, argu);
      a1=(String)n.f1.accept(this, argu);
      ans=a1+"MOVE v0 HALLOCATE v0\n";
      return (R)ans;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      String a1,a2,ans;
      
      a1=(String)n.f1.accept(this, argu);
      a2=(String)n.f2.accept(this, argu);
      
      if(spilled.contains(a1)) 
		  ans="ALOAD v1 SPILLEDARG "+name.get(a1)+"\n";
      else 
    	  ans="MOVE v1 "+name.get(a1)+"\n";
      
      ans=ans+a2+"MOVE v0 "+(String)n.f0.accept(this, argu)+" v1 v0\n";
      return (R)ans;
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
      return n.f0.accept(this, argu);
//      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n, A argu) {
      R _ret=null;
      simplfl=true;
      String s1=(String)n.f0.accept(this, argu);
      simplfl=false;
      return (R)s1;
//      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n, A argu) {
      R _ret=null;
//      n.f0.accept(this, argu);
//      n.f1.accept(this, argu);
      String ans="";
      String s1=(String)n.f1.f0.tokenImage;
      
      if(tempo) {
    	  
    	  fun_arg.add((String)n.f1.f0.tokenImage);
    	  return _ret;
      }
      
      if(simplfl) {
    	  if(spilled.contains(s1)) 
    		  ans="ALOAD v0 SPILLEDARG "+name.get(s1)+"\n";
	      else 
	    	  ans="MOVE v0 "+name.get(s1)+"\n";  
   		}
      else {
    	  ans=s1;
      }
      return (R)ans;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String ans;
      ans="MOVE v0 "+(String)n.f0.tokenImage+"\n";
      return (R)ans;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      if(flabel) {
	      line_no++;
      }
      if(simplfl)
    	  return (R)("MOVE v0 "+(String)n.f0.tokenImage+"\n");	  
      else
    	  return n.f0.accept(this, argu);
//      return _ret;
   }

}
