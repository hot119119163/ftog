/*
 * Copyright (C) 2008 Mattias Ånstrand.
 * 
 * This file is part of Flex DTO Generator.
 *
 * Flex DTO Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Flex DTO Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Flex DTO Generator.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Created on 2007-12-29
 */
package ftog.visitor;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.SuperMemberAccessExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mattias Ånstrand
 */

public class BaseVisitor implements VoidVisitor<Object> {


    private void acceptMembers(List<BodyDeclaration> members, Object arg) {
        for (BodyDeclaration member : members)
            member.accept(this, arg);
    }



    private void acceptAnnotations(List<AnnotationExpr> annotations, Object arg) {
        if (annotations != null) {
            for (AnnotationExpr a : annotations) {
                a.accept(this, arg);
            }
        }
    }

    private void acceptTypeArgs(List<Type> args, Object arg) {
        if (args != null)
            for (Iterator<Type> i = args.iterator(); i.hasNext();) {
                Type t = i.next();
                t.accept(this, arg);
            }
    }

    private void acceptTypeParameters(List<TypeParameter> args, Object arg) {
        if (args != null) 
            for (Iterator<TypeParameter> i = args.iterator(); i.hasNext();) {
                TypeParameter t = i.next();
                t.accept(this, arg);
            }
    }

    public void visit(Node n, Object arg) {
        throw new IllegalStateException(n.getClass().getName());
    }

    public void visit(CompilationUnit n, Object arg) {
        if (n.pakage != null)
            n.pakage.accept(this, arg);
        if (n.imports != null) 
            for (ImportDeclaration i : n.imports) 
                i.accept(this, arg);
        if (n.types != null)
            for (TypeDeclaration i : n.types) 
                i.accept(this, arg);

    }

    public void visit(PackageDeclaration n, Object arg) {
        n.name.accept(this, arg);
    }

    public void visit(NameExpr n, Object arg) {
    }

    public void visit(QualifiedNameExpr n, Object arg) {
        n.qualifier.accept(this, arg);
    }

    public void visit(ImportDeclaration n, Object arg) {
        n.name.accept(this, arg);
    }

    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
    	acceptAnnotations(n.annotations, arg);
    	acceptTypeParameters(n.typeParameters, arg);
        if (n.extendsList != null) {
            for (Iterator<ClassOrInterfaceType> i = n.extendsList.iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }

        if (n.implementsList != null) {
            for (Iterator<ClassOrInterfaceType> i = n.implementsList.iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }
        if (n.members != null) {
            acceptMembers(n.members, arg);
        }
    }

    public void visit(EmptyTypeDeclaration n, Object arg) {
    }

    public void visit(ClassOrInterfaceType n, Object arg) {
        if (n.scope != null) {
            n.scope.accept(this, arg);
        }
        acceptTypeArgs(n.typeArgs, arg);
    }

    public void visit(TypeParameter n, Object arg) {
        if (n.typeBound != null) {
            for (Iterator<ClassOrInterfaceType> i = n.typeBound.iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }
        }
    }

    public void visit(PrimitiveType n, Object arg) {
    }

    public void visit(ReferenceType n, Object arg) {
        n.type.accept(this, arg);
    }

    public void visit(WildcardType n, Object arg) {
        if (n.ext != null) 
            n.ext.accept(this, arg);
        if (n.sup != null) 
            n.sup.accept(this, arg);
    }

    public void visit(FieldDeclaration n, Object arg) {
    	acceptAnnotations(n.annotations, arg);
    	n.type.accept(this, arg);
        for (Iterator<VariableDeclarator> i = n.variables.iterator(); i.hasNext();) {
            VariableDeclarator var = i.next();
            var.accept(this, arg);
        }
    }

    public void visit(VariableDeclarator n, Object arg) {
        n.id.accept(this, arg);
        if (n.init != null) 
            n.init.accept(this, arg);
    }

    public void visit(VariableDeclaratorId n, Object arg) {
    }

    public void visit(ArrayInitializerExpr n, Object arg) {
        if (n.values != null)
            for (Iterator<Expression> i = n.values.iterator(); i.hasNext();) {
                Expression expr = i.next();
                expr.accept(this, arg);
            }
    }

    public void visit(VoidType n, Object arg) {
    }

    public void visit(ArrayAccessExpr n, Object arg) {
        n.name.accept(this, arg);
        n.index.accept(this, arg);
    }

    public void visit(ArrayCreationExpr n, Object arg) {
        n.type.accept(this, arg);
        acceptTypeArgs(n.typeArgs, arg);
        if (n.dimensions != null)  
            for (Expression dim : n.dimensions) 
                dim.accept(this, arg);
        else
            n.initializer.accept(this, arg);
    }

    public void visit(AssignExpr n, Object arg) {
        n.target.accept(this, arg);
        n.value.accept(this, arg);
    }

    public void visit(BinaryExpr n, Object arg) {
        n.left.accept(this, arg);
        n.right.accept(this, arg);
    }

    public void visit(CastExpr n, Object arg) {
        n.type.accept(this, arg);
        n.expr.accept(this, arg);
    }

    public void visit(ClassExpr n, Object arg) {
        n.type.accept(this, arg);
    }

    public void visit(ConditionalExpr n, Object arg) {
        n.condition.accept(this, arg);
        n.thenExpr.accept(this, arg);
        n.elseExpr.accept(this, arg);
    }

    public void visit(EnclosedExpr n, Object arg) {
        n.inner.accept(this, arg);
    }

    public void visit(FieldAccessExpr n, Object arg) {
        n.scope.accept(this, arg);
    }

    public void visit(InstanceOfExpr n, Object arg) {
        n.expr.accept(this, arg);
        n.type.accept(this, arg);
    }

    public void visit(CharLiteralExpr n, Object arg) {
    }

    public void visit(DoubleLiteralExpr n, Object arg) {
    }

    public void visit(IntegerLiteralExpr n, Object arg) {
    }

    public void visit(LongLiteralExpr n, Object arg) {
    }

    public void visit(IntegerLiteralMinValueExpr n, Object arg) {
    }

    public void visit(LongLiteralMinValueExpr n, Object arg) {
    }

    public void visit(StringLiteralExpr n, Object arg) {
    }

    public void visit(BooleanLiteralExpr n, Object arg) {
    }

    public void visit(NullLiteralExpr n, Object arg) {
    }

    public void visit(ThisExpr n, Object arg) {
        if (n.classExpr != null) 
            n.classExpr.accept(this, arg);
    }

    public void visit(SuperExpr n, Object arg) {
        if (n.classExpr != null)
            n.classExpr.accept(this, arg);
    }

    public void visit(MethodCallExpr n, Object arg) {
        if (n.scope != null)
            n.scope.accept(this, arg);
    	acceptTypeArgs(n.typeArgs, arg);
        if (n.args != null)
            for (Iterator<Expression> i = n.args.iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
    }

    public void visit(ObjectCreationExpr n, Object arg) {
        if (n.scope != null)
            n.scope.accept(this, arg);
 
    	acceptTypeArgs(n.typeArgs, arg);
        n.type.accept(this, arg);
        if (n.args != null)
            for (Iterator<Expression> i = n.args.iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
        
        if (n.anonymousClassBody != null) 
            acceptMembers(n.anonymousClassBody, arg);
    }

    public void visit(SuperMemberAccessExpr n, Object arg) {
    }

    public void visit(UnaryExpr n, Object arg) {
        n.expr.accept(this, arg);
    }

    public void visit(ConstructorDeclaration n, Object arg) {
    	acceptAnnotations(n.annotations, arg);
    	acceptTypeParameters(n.typeParameters, arg);
        if (n.parameters != null)
            for (Iterator<Parameter> i = n.parameters.iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
            }
        
        if (n.throws_ != null)
            for (Iterator<NameExpr> i = n.throws_.iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
            }
        n.block.accept(this, arg);
    }

    public void visit(MethodDeclaration n, Object arg) {
    	acceptAnnotations(n.annotations, arg);
    	acceptTypeParameters(n.typeParameters, arg);
        n.type.accept(this, arg);
        if (n.parameters != null)
            for (Iterator<Parameter> i = n.parameters.iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
            }

      
        if (n.throws_ != null)
            for (Iterator<NameExpr> i = n.throws_.iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
            }

        if (n.block != null)
            n.block.accept(this, arg);
    }

    public void visit(Parameter n, Object arg) {
        n.type.accept(this, arg);
        n.id.accept(this, arg);
    }

    public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
        if (n.isThis)
        	acceptTypeArgs(n.typeArgs, arg);
       	else {
       		if(n.expr != null)
       			n.expr.accept(this, arg);
       		
        	acceptTypeArgs(n.typeArgs, arg);
       	}
        
        if (n.args != null)
            for (Iterator<Expression> i = n.args.iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }
    }

    public void visit(VariableDeclarationExpr n, Object arg) {
        n.type.accept(this, arg);
        for (Iterator<VariableDeclarator> i = n.vars.iterator(); i.hasNext();) {
            VariableDeclarator v = i.next();
            v.accept(this, arg);
        }
    }

    public void visit(TypeDeclarationStmt n, Object arg) {
        n.typeDecl.accept(this, arg);
    }

    public void visit(AssertStmt n, Object arg) {
        n.check.accept(this, arg);
        if (n.msg != null)
            n.msg.accept(this, arg);
    }

    public void visit(BlockStmt n, Object arg) {
        if (n.stmts != null) {
            for (Statement s : n.stmts) 
                s.accept(this, arg);
        }
    }

    public void visit(LabeledStmt n, Object arg) {
        n.stmt.accept(this, arg);
    }

    public void visit(EmptyStmt n, Object arg) {
    }

    public void visit(ExpressionStmt n, Object arg) {
        n.expr.accept(this, arg);
    }

    public void visit(SwitchStmt n, Object arg) {
        n.selector.accept(this, arg);
        if (n.entries != null)
            for (SwitchEntryStmt e : n.entries) {
                e.accept(this, arg);
            }
    }

    public void visit(SwitchEntryStmt n, Object arg) {
        if (n.label != null)
            n.label.accept(this, arg);
        if (n.stmts != null)
            for (Statement s : n.stmts) 
                s.accept(this, arg);
    }

    public void visit(BreakStmt n, Object arg) {
    }

    public void visit(ReturnStmt n, Object arg) {
        if (n.expr != null)
            n.expr.accept(this, arg);
    }

    public void visit(EnumDeclaration n, Object arg) {
    	acceptAnnotations(n.annotations, arg);
        if (n.implementsList != null)
            for (Iterator<ClassOrInterfaceType> i = n.implementsList.iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
            }

        if (n.entries != null) 
            for (Iterator<EnumConstantDeclaration> i = n.entries.iterator(); i.hasNext();) {
                EnumConstantDeclaration e = i.next();
                e.accept(this, arg);
            }
        
        if (n.members != null) 
            acceptMembers(n.members, arg);
    }

    public void visit(EnumConstantDeclaration n, Object arg) {
    	acceptAnnotations(n.annotations, arg);
        if (n.args != null)
            for (Iterator<Expression> i = n.args.iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }

        if (n.classBody != null) 
            acceptMembers(n.classBody, arg);
    }

    public void visit(EmptyMemberDeclaration n, Object arg) {
    }

    public void visit(InitializerDeclaration n, Object arg) {
        n.block.accept(this, arg);
    }

    public void visit(IfStmt n, Object arg) {
        n.condition.accept(this, arg);
        n.thenStmt.accept(this, arg);
        if (n.elseStmt != null)
            n.elseStmt.accept(this, arg);
    }

    public void visit(WhileStmt n, Object arg) {
        n.condition.accept(this, arg);
        n.body.accept(this, arg);
    }

    public void visit(ContinueStmt n, Object arg) {
    }

    public void visit(DoStmt n, Object arg) {
        n.body.accept(this, arg);
        n.condition.accept(this, arg);
    }

    public void visit(ForeachStmt n, Object arg) {
        n.var.accept(this, arg);
        n.iterable.accept(this, arg);
        n.body.accept(this, arg);
    }

    public void visit(ForStmt n, Object arg) {
        if (n.init != null)
           for (Iterator<Expression> i = n.init.iterator(); i.hasNext();) {
               Expression e = i.next();
               e.accept(this, arg);
           }
 
        if (n.compare != null) {
            n.compare.accept(this, arg);
        }
        
        if (n.update != null)
            for (Iterator<Expression> i = n.update.iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
            }

        n.body.accept(this, arg);
    }

    public void visit(ThrowStmt n, Object arg) {
        n.expr.accept(this, arg);
    }

    public void visit(SynchronizedStmt n, Object arg) {
        n.expr.accept(this, arg);
        n.block.accept(this, arg);
    }

    public void visit(TryStmt n, Object arg) {
        n.tryBlock.accept(this, arg);
        if (n.catchs != null) 
            for (CatchClause c : n.catchs)
                c.accept(this, arg);

        if (n.finallyBlock != null) 
            n.finallyBlock.accept(this, arg);
    }

    public void visit(CatchClause n, Object arg) {
        n.except.accept(this, arg);
        n.catchBlock.accept(this, arg);

    }

    public void visit(AnnotationDeclaration n, Object arg) {
        acceptAnnotations(n.annotations, arg);
    	if (n.members != null) 
            acceptMembers(n.members, arg);    
    }

    
    
    public void visit(AnnotationMemberDeclaration n, Object arg) {
        acceptAnnotations(n.annotations, arg);
        n.type.accept(this, arg);
        if (n.defaultValue != null)
            n.defaultValue.accept(this, arg);
    }

    public void visit(MarkerAnnotationExpr n, Object arg) {
        n.name.accept(this, arg);
    }

    public void visit(SingleMemberAnnotationExpr n, Object arg) {
        n.name.accept(this, arg);
        n.memberValue.accept(this, arg);
    }

    public void visit(NormalAnnotationExpr n, Object arg) {
        n.name.accept(this, arg);
        for (Iterator<MemberValuePair> i = n.pairs.iterator(); i.hasNext();) {
            MemberValuePair m = i.next();
            m.accept(this, arg);
        }
    }

    public void visit(MemberValuePair n, Object arg) {
        n.value.accept(this, arg);
    }
}
