package genInfo;

import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.JaccardSimilarity;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class provides an empty implementation of {@link Java8ParserVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class ExtendedVisitorsJava<T> extends Java8ParserBaseVisitor<T>{
    private ArrayList<FunctionSats> functions;
    private Stack<FunctionSats> scope = new Stack<>();
    private Stack<String> logicScope = new Stack<>();
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLiteral(Java8Parser.LiteralContext ctx) { return (T) (ctx.getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.AnnotationContext x : ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        if(ctx.numericType()!=null){
            codeAux.append((String) visitNumericType(ctx.numericType()));
        }else{
            codeAux.append("boolean ");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitNumericType(Java8Parser.NumericTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitIntegralType(Java8Parser.IntegralTypeContext ctx) { return (T) (ctx.getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFloatingPointType(Java8Parser.FloatingPointTypeContext ctx) { return (T) (ctx.getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitReferenceType(Java8Parser.ReferenceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx) { return (T) (ctx.getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassType(Java8Parser.ClassTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.classOrInterfaceType()!=null){
            codeAux.append((String) visitClassOrInterfaceType(ctx.classOrInterfaceType())+".");
        }
        for(Java8Parser.AnnotationContext x: ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassType_lf_classOrInterfaceType(Java8Parser.ClassType_lf_classOrInterfaceTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(".");
        for(Java8Parser.AnnotationContext x: ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassType_lfno_classOrInterfaceType(Java8Parser.ClassType_lfno_classOrInterfaceTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.AnnotationContext x: ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceType(Java8Parser.InterfaceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceType_lf_classOrInterfaceType(Java8Parser.InterfaceType_lf_classOrInterfaceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceType_lfno_classOrInterfaceType(Java8Parser.InterfaceType_lfno_classOrInterfaceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeVariable(Java8Parser.TypeVariableContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.AnnotationContext x: ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArrayType(Java8Parser.ArrayTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitDims(Java8Parser.DimsContext ctx) { return (T) (ctx.getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeParameter(Java8Parser.TypeParameterContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.TypeParameterModifierContext x: ctx.typeParameterModifier()){
            codeAux.append((String) visitTypeParameterModifier(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeBound()!=null){
            codeAux.append((String) visitTypeBound(ctx.typeBound()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeParameterModifier(Java8Parser.TypeParameterModifierContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeBound(Java8Parser.TypeBoundContext ctx) {
        if(ctx.typeVariable()==null){
            StringBuffer codeAux = new StringBuffer();
            codeAux.append("extends"+((String) visitClassOrInterfaceType(ctx.classOrInterfaceType())));
            for(Java8Parser.AdditionalBoundContext x: ctx.additionalBound()){
                codeAux.append((String) visitAdditionalBound(x));
            }
            return (T) codeAux.toString();
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAdditionalBound(Java8Parser.AdditionalBoundContext ctx) { return (T) ("& "+((String) visitInterfaceType(ctx.interfaceType()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeArguments(Java8Parser.TypeArgumentsContext ctx) { return (T) ("< "+visitTypeArgumentList(ctx.typeArgumentList())+"> "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeArgumentList(Java8Parser.TypeArgumentListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitTypeArgument(ctx.typeArgument(0)));
        for(int i = 1; i<ctx.typeArgument().size();i++){
            codeAux.append(", "+((String) visitTypeArgument(ctx.typeArgument(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeArgument(Java8Parser.TypeArgumentContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitWildcard(Java8Parser.WildcardContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.AnnotationContext x : ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append("? ");
        if(ctx.wildcardBounds()!=null){
            codeAux.append((String) visitWildcardBounds(ctx.wildcardBounds()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitWildcardBounds(Java8Parser.WildcardBoundsContext ctx) {
        if(ctx.getText().substring(0,7).contains("extends")){
            return (T) ("extends "+((String) visitReferenceType(ctx.referenceType())));
        }
        return (T) ("super "+((String) visitReferenceType(ctx.referenceType())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPackageName(Java8Parser.PackageNameContext ctx) {
        if(ctx.packageName()==null){
            return (T) (ctx.Identifier().getText()+" ");
        }
        return (T) (((String) visitPackageName(ctx.packageName()))+". "+ctx.Identifier().getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeName(Java8Parser.TypeNameContext ctx) {
        if(ctx.packageOrTypeName()==null){
            return (T) (ctx.Identifier().getText()+" ");
        }
        return (T) (((String) visitPackageOrTypeName(ctx.packageOrTypeName()))+". "+ctx.Identifier().getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPackageOrTypeName(Java8Parser.PackageOrTypeNameContext ctx) {
        if(ctx.packageOrTypeName()==null){
            return (T) (ctx.Identifier().getText()+" ");
        }
        return (T) (((String) visitPackageOrTypeName(ctx.packageOrTypeName()))+". "+ctx.Identifier().getText()+" ");}
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExpressionName(Java8Parser.ExpressionNameContext ctx) {
        if(ctx.ambiguousName()==null){
            return (T) (ctx.Identifier().getText()+" ");
        }
        return (T) (((String) visitAmbiguousName(ctx.ambiguousName()))+"."+ctx.Identifier().getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodName(Java8Parser.MethodNameContext ctx) { return (T) (ctx.Identifier().getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAmbiguousName(Java8Parser.AmbiguousNameContext ctx) {
        if(ctx.ambiguousName()==null){
            return (T) (ctx.Identifier().getText()+" ");
        }
        return (T) (((String) visitAmbiguousName(ctx.ambiguousName()))+". "+ctx.Identifier().getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.packageDeclaration()!=null){
            codeAux.append((String) visitPackageDeclaration(ctx.packageDeclaration()));
        }
        for(Java8Parser.ImportDeclarationContext x : ctx.importDeclaration()){
            codeAux.append(visitImportDeclaration(x));
        }for(Java8Parser.TypeDeclarationContext x : ctx.typeDeclaration()){
            codeAux.append(visitTypeDeclaration(x));
        }
        getDuplicityInd();
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPackageDeclaration(Java8Parser.PackageDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.PackageModifierContext x : ctx.packageModifier()){
            codeAux.append(visitPackageModifier(x));
        }
        codeAux.append("package "+visitPackageName(ctx.packageName())+"; \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPackageModifier(Java8Parser.PackageModifierContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitImportDeclaration(Java8Parser.ImportDeclarationContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSingleTypeImportDeclaration(Java8Parser.SingleTypeImportDeclarationContext ctx) { return (T) ("import "+((String) visitTypeName(ctx.typeName()))+"; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeImportOnDemandDeclaration(Java8Parser.TypeImportOnDemandDeclarationContext ctx) { return (T) ("import "+((String) visitPackageOrTypeName(ctx.packageOrTypeName()))+". * ; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSingleStaticImportDeclaration(Java8Parser.SingleStaticImportDeclarationContext ctx) { return (T) ("import static "+((String) visitTypeName(ctx.typeName()))+"."+ctx.Identifier().getText()+" ; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStaticImportOnDemandDeclaration(Java8Parser.StaticImportOnDemandDeclarationContext ctx) { return (T) ("import static "+((String) visitTypeName(ctx.typeName()))+". * ; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeDeclaration(Java8Parser.TypeDeclarationContext ctx) {
        if(ctx.getText().equals(";")){
            return (T) ("; \n");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.ClassModifierContext x : ctx.classModifier()){
            codeAux.append(visitClassModifier(x));
        }
        codeAux.append("class "+ctx.Identifier().getText()+" ");
        if(ctx.typeParameters()!=null){
            codeAux.append((String) visitTypeParameters(ctx.typeParameters()));
        }
        if(ctx.superclass()!=null){
            codeAux.append((String) visitSuperclass(ctx.superclass()));
        }
        if(ctx.superinterfaces()!=null){
            codeAux.append((String) visitSuperinterfaces(ctx.superinterfaces()));
        }
        codeAux.append((String) visitClassBody(ctx.classBody()));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassModifier(Java8Parser.ClassModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeParameters(Java8Parser.TypeParametersContext ctx) { return (T) ("< "+((String) visitTypeParameterList(ctx.typeParameterList()))+"> "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeParameterList(Java8Parser.TypeParameterListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitTypeParameter(ctx.typeParameter(0)));
        for(int i = 1; i< ctx.typeParameter().size();i++){
            codeAux.append(", "+visitTypeParameter(ctx.typeParameter(i)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSuperclass(Java8Parser.SuperclassContext ctx) { return (T) ("extnds "+((String) visitClassType(ctx.classType()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSuperinterfaces(Java8Parser.SuperinterfacesContext ctx) { return (T) ("implements "+((String) visitInterfaceTypeList(ctx.interfaceTypeList()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceTypeList(Java8Parser.InterfaceTypeListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitInterfaceType(ctx.interfaceType(0)));
        for(int i = 1; i< ctx.interfaceType().size();i++){
            codeAux.append(", "+visitInterfaceType(ctx.interfaceType(i)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassBody(Java8Parser.ClassBodyContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        for(Java8Parser.ClassBodyDeclarationContext x : ctx.classBodyDeclaration()){
            codeAux.append(visitClassBodyDeclaration(x));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassMemberDeclaration(Java8Parser.ClassMemberDeclarationContext ctx) {
        if(ctx.getText().equals(";")){
            return (T) ("; \n");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.FieldModifierContext x : ctx.fieldModifier()){
            codeAux.append((String) visitFieldModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorList(ctx.variableDeclaratorList()))+"; \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFieldModifier(Java8Parser.FieldModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitVariableDeclaratorList(Java8Parser.VariableDeclaratorListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitVariableDeclarator(ctx.variableDeclarator(0)));
        for(int i = 1; i< ctx.variableDeclarator().size();i++){
            codeAux.append(", "+visitVariableDeclarator(ctx.variableDeclarator(i)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitVariableDeclaratorId(ctx.variableDeclaratorId()));
        if(ctx.variableInitializer()!=null){
            codeAux.append("= "+visitVariableInitializer(ctx.variableInitializer()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) {
        if(ctx.dims()==null){
            return (T) (ctx.Identifier().getText()+" ");
        }
        return (T) (ctx.Identifier().getText()+" "+visitDims(ctx.dims()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitVariableInitializer(Java8Parser.VariableInitializerContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannType(Java8Parser.UnannTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannPrimitiveType(Java8Parser.UnannPrimitiveTypeContext ctx) {
        if(ctx.numericType()==null){
            return (T) "booblean ";
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannReferenceType(Java8Parser.UnannReferenceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannClassOrInterfaceType(Java8Parser.UnannClassOrInterfaceTypeContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannClassType(Java8Parser.UnannClassTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.unannClassOrInterfaceType()!=null){
            codeAux.append(((String) visitUnannClassOrInterfaceType(ctx.unannClassOrInterfaceType()))+".");
            for(Java8Parser.AnnotationContext x : ctx.annotation()){
                codeAux.append((String) visitAnnotation(x));
            }
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannClassType_lf_unannClassOrInterfaceType(Java8Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(".");
        for(Java8Parser.AnnotationContext x : ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannClassType_lfno_unannClassOrInterfaceType(Java8Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext ctx) {
        if(ctx.typeArguments()!=null){
            return (T) (ctx.Identifier().getText()+" "+((String) visitTypeArguments(ctx.typeArguments())));
        }
        return (T) (ctx.Identifier().getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannInterfaceType(Java8Parser.UnannInterfaceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannInterfaceType_lf_unannClassOrInterfaceType(Java8Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannInterfaceType_lfno_unannClassOrInterfaceType(Java8Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannTypeVariable(Java8Parser.UnannTypeVariableContext ctx) { return (T) (ctx.Identifier().getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnannArrayType(Java8Parser.UnannArrayTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.unannPrimitiveType()!=null){
            codeAux.append((String) visitUnannPrimitiveType(ctx.unannPrimitiveType()));
        }else if(ctx.unannClassOrInterfaceType()!=null){
            codeAux.append((String) visitUnannClassOrInterfaceType(ctx.unannClassOrInterfaceType()));
        }else{
            codeAux.append((String) visitUnannTypeVariable(ctx.unannTypeVariable()));
        }
        codeAux.append((String) visitDims(ctx.dims()));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        String currentName = ctx.methodHeader().methodDeclarator().Identifier().getText();
        for(FunctionSats func : functions) {
            if (func.getName().equals(currentName)){
                scope.push(func);
                break;
            }
        }
        for(int i=0; i< ctx.methodModifier().size();i++){
            codeAux.append((String) visitMethodModifier(ctx.methodModifier(i)));
        }
        codeAux.append((String) visitMethodHeader(ctx.methodHeader())+((String) visitMethodBody(ctx.methodBody())));
        //System.out.println("Func "+currentName+"\n"+codeAux.toString());
        scope.peek().getRawCode().put(codeAux.toString(),"complete");
        scope.pop();
        return null;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodModifier(Java8Parser.MethodModifierContext ctx) {
        if(ctx.annotation()!=null){
            return (T) (visitChildren(ctx));
        }else{
            return (T) (ctx.getText()+" ");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodHeader(Java8Parser.MethodHeaderContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.typeParameters()!=null){
            codeAux.append((String) visitTypeParameters(ctx.typeParameters()));
            for(int i=0; i<ctx.annotation().size();i++){
                codeAux.append(((String) visitAnnotation(ctx.annotation(i))));
            }
        }
        codeAux.append(((String) visitResult(ctx.result()))+((String) visitMethodDeclarator(ctx.methodDeclarator())));
        if(ctx.throws_()!=null){
            codeAux.append((String) visitThrows_(ctx.throws_()));
        }
        return (T) codeAux.toString();
    }
    /**
     *
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitResult(Java8Parser.ResultContext ctx) {
        if(ctx.getText().contains("void")){
            return (T) ("void ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(ctx.Identifier().getText()+" ( ");
        if(ctx.formalParameterList()!=null){
            codeAux.append((String) visitFormalParameterList(ctx.formalParameterList()));
        }
        codeAux.append(") \n");
        if(ctx.dims()!=null){
            codeAux.append(visitDims(ctx.dims()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFormalParameterList(Java8Parser.FormalParameterListContext ctx) {
        if(ctx.formalParameters()!=null){
            return (T) (((String) visitFormalParameters(ctx.formalParameters()))+", "+((String) visitLastFormalParameter(ctx.lastFormalParameter())));
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFormalParameters(Java8Parser.FormalParametersContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        int index;
        if(!ctx.formalParameter().isEmpty()){
            codeAux.append((String) visitFormalParameter(ctx.formalParameter(0)));
            index=1;
        }else{
            codeAux.append((String) visitReceiverParameter(ctx.receiverParameter()));
            index=0;
        }
        for(int i =index; i<ctx.formalParameter().size();i++){
            codeAux.append(", "+((String) visitFormalParameter(ctx.formalParameter(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFormalParameter(Java8Parser.FormalParameterContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.VariableModifierContext x:ctx.variableModifier()){
            codeAux.append(((String) visitVariableModifier(x)));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorId(ctx.variableDeclaratorId())));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitVariableModifier(Java8Parser.VariableModifierContext ctx) {
        if(ctx.annotation()!=null){
            return visitChildren(ctx);
        }
        return (T) "final ";
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
        if(ctx.formalParameter()==null){
            StringBuffer codeAux = new StringBuffer();
            for(Java8Parser.VariableModifierContext x:ctx.variableModifier()){
                codeAux.append(((String) visitVariableModifier(x)));
            }
            codeAux.append((String) visitUnannType(ctx.unannType()));
            for(Java8Parser.AnnotationContext x:ctx.annotation()){
                codeAux.append(((String) visitAnnotation(x)));
            }
            codeAux.append("... "+((String) visitVariableDeclaratorId(ctx.variableDeclaratorId())));
            return (T) codeAux.toString();
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitReceiverParameter(Java8Parser.ReceiverParameterContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitUnannType(ctx.unannType()));
        for(Java8Parser.AnnotationContext x:ctx.annotation()){
            codeAux.append(((String) visitAnnotation(x)));
        }
        codeAux.append((String) visitUnannType(ctx.unannType()));
        if(ctx.Identifier()!=null){
            codeAux.append(ctx.Identifier().getText()+" . this");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitThrows_(Java8Parser.Throws_Context ctx) {
        return (T) ("throws "+((String) visitChildren(ctx)));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitExceptionType(ctx.exceptionType(0)));
        for(int i=1; i<ctx.exceptionType().size();i++){
            codeAux.append(", "+((String) visitExceptionType(ctx.exceptionType(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExceptionType(Java8Parser.ExceptionTypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodBody(Java8Parser.MethodBodyContext ctx) {
        if(ctx.block()==null){
            return (T) "; ";
        }
        return visitBlock(ctx.block());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInstanceInitializer(Java8Parser.InstanceInitializerContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStaticInitializer(Java8Parser.StaticInitializerContext ctx) {
        return (T) ("static "+((String) visitChildren(ctx)));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.ConstructorModifierContext x:ctx.constructorModifier()){
            codeAux.append((String) visitConstructorModifier(x));
        }
        codeAux.append((String) visitConstructorDeclarator(ctx.constructorDeclarator()));
        if(ctx.throws_()!=null){
            codeAux.append((String) visitThrows_(ctx.throws_()));
        }
        codeAux.append((String) visitConstructorBody(ctx.constructorBody()));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstructorModifier(Java8Parser.ConstructorModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.typeParameters()!=null){
            codeAux.append((String) visitTypeParameters(ctx.typeParameters()));
        }
        codeAux.append(((String) visitSimpleTypeName(ctx.simpleTypeName()))+"( ");
        if(ctx.formalParameterList()!=null){
            codeAux.append((String) visitFormalParameterList(ctx.formalParameterList()));
        }
        codeAux.append(") ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSimpleTypeName(Java8Parser.SimpleTypeNameContext ctx) {
        return (T) (ctx.Identifier().getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstructorBody(Java8Parser.ConstructorBodyContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        if(ctx.explicitConstructorInvocation()!=null){
            codeAux.append((String) visitExplicitConstructorInvocation(ctx.explicitConstructorInvocation()));
        }
        if(ctx.blockStatements()!=null){
            codeAux.append(visitBlockStatements(ctx.blockStatements()));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.expressionName()!=null){
            codeAux.append(((String) visitExpressionName(ctx.expressionName()))+".");
        }else if(ctx.primary()!=null){
            codeAux.append(((String) visitPrimary(ctx.primary()))+".");
        }
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        if(ctx.getText().contains("this")){
            codeAux.append("this ( ");
        }else{
            codeAux.append("super ( ");
        }
        if(ctx.argumentList()!=null){
            codeAux.append((String) visitArgumentList(ctx.argumentList()));
        }
        codeAux.append(") ; \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.ClassModifierContext x : ctx.classModifier()){
            codeAux.append((String) visitClassModifier(x));
        }
        codeAux.append("enum "+ctx.Identifier().getText()+" ");
        if(ctx.superinterfaces()!=null){
            codeAux.append((String) visitSuperinterfaces(ctx.superinterfaces()));
        }
        codeAux.append((String) visitEnumBody(ctx.enumBody()));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumBody(Java8Parser.EnumBodyContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        if(ctx.enumConstantList()!=null){
            codeAux.append((String) visitEnumConstantList(ctx.enumConstantList()));
        }
        if(ctx.getText().contains(",")){
            codeAux.append(", ");
        }
        if(ctx.enumBodyDeclarations()!=null){
            codeAux.append((String) visitEnumBodyDeclarations(ctx.enumBodyDeclarations()));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumConstantList(Java8Parser.EnumConstantListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitEnumConstant(ctx.enumConstant(0)));
        for(int i = 1; i<ctx.enumConstant().size();i++){
            codeAux.append(", "+((String) visitEnumConstant(ctx.enumConstant(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumConstant(Java8Parser.EnumConstantContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.EnumConstantModifierContext x : ctx.enumConstantModifier()){
            codeAux.append((String) visitEnumConstantModifier(x));
        }
        codeAux.append(ctx.Identifier().getText());
        if(ctx.getText().contains("(")){
            codeAux.append("( ");
            if(ctx.argumentList()!=null){
                codeAux.append((String) visitArgumentList(ctx.argumentList()));
            }
            codeAux.append(") ");
        }
        if(ctx.classBody()!=null){
            codeAux.append((String) visitClassBody(ctx.classBody()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumConstantModifier(Java8Parser.EnumConstantModifierContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumBodyDeclarations(Java8Parser.EnumBodyDeclarationsContext ctx) { return (T) (";\n"+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.InterfaceModifierContext x : ctx.interfaceModifier()){
            codeAux.append((String) visitInterfaceModifier(x));
        }
        codeAux.append("interface "+ctx.Identifier().getText()+" ");
        if(ctx.typeParameters()!=null){
            codeAux.append(visitTypeParameters(ctx.typeParameters()));
        }
        if(ctx.extendsInterfaces()!=null){
            codeAux.append((String) visitExtendsInterfaces(ctx.extendsInterfaces()));
        }
        codeAux.append((String) visitInterfaceBody(ctx.interfaceBody()));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceModifier(Java8Parser.InterfaceModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExtendsInterfaces(Java8Parser.ExtendsInterfacesContext ctx) { return (T) ("extends "+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceBody(Java8Parser.InterfaceBodyContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        for(Java8Parser.InterfaceMemberDeclarationContext x : ctx.interfaceMemberDeclaration()){
            codeAux.append(visitInterfaceMemberDeclaration(x));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceMemberDeclaration(Java8Parser.InterfaceMemberDeclarationContext ctx) {
        if(ctx.getText().equals(";")){
            return (T) ("; ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstantDeclaration(Java8Parser.ConstantDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.ConstantModifierContext x : ctx.constantModifier()){
            codeAux.append((String) visitConstantModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorList(ctx.variableDeclaratorList()))+"; \n");
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstantModifier(Java8Parser.ConstantModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.InterfaceMethodModifierContext x : ctx.interfaceMethodModifier()){
            codeAux.append((String) visitInterfaceMethodModifier(x));
        }
        codeAux.append(((String) visitMethodHeader(ctx.methodHeader()))+((String) visitMethodBody(ctx.methodBody())));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInterfaceMethodModifier(Java8Parser.InterfaceMethodModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.InterfaceModifierContext x : ctx.interfaceModifier()){
            codeAux.append((String) visitInterfaceModifier(x));
        }
        codeAux.append("@ interface "+ctx.Identifier().getText()+" "+((String) visitAnnotationTypeBody(ctx.annotationTypeBody())));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        for(Java8Parser.AnnotationTypeMemberDeclarationContext x : ctx.annotationTypeMemberDeclaration()){
            codeAux.append((String) visitAnnotationTypeMemberDeclaration(x));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAnnotationTypeMemberDeclaration(Java8Parser.AnnotationTypeMemberDeclarationContext ctx) {
        if(ctx.getText().equals(";")){
            return (T) ("; \n");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.AnnotationTypeElementModifierContext x : ctx.annotationTypeElementModifier()){
            codeAux.append((String) visitAnnotationTypeElementModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+ctx.Identifier().getText()+" ( ) ");
        if(ctx.dims()!=null){
            codeAux.append((String) visitDims(ctx.dims()));
        }
        if(ctx.defaultValue()!=null){
            codeAux.append((String) visitDefaultValue(ctx.defaultValue()));
        }
        codeAux.append("; \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAnnotationTypeElementModifier(Java8Parser.AnnotationTypeElementModifierContext ctx) {
        if(ctx.annotation()==null){
            return (T) (ctx.getText()+" ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitDefaultValue(Java8Parser.DefaultValueContext ctx) { return (T) ("default "+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAnnotation(Java8Parser.AnnotationContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitNormalAnnotation(Java8Parser.NormalAnnotationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("@ "+((String) visitTypeName(ctx.typeName()))+"( ");
        if(ctx.elementValuePairList()!=null){
            codeAux.append((String) visitElementValuePairList(ctx.elementValuePairList()));
        }
        codeAux.append(") ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitElementValuePairList(Java8Parser.ElementValuePairListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitElementValuePair(ctx.elementValuePair(0)));
        for(int i = 1; i<ctx.elementValuePair().size();i++){
            codeAux.append(", "+((String) visitElementValuePair(ctx.elementValuePair(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitElementValuePair(Java8Parser.ElementValuePairContext ctx) { return (T) (ctx.Identifier().getText()+" = "+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitElementValue(Java8Parser.ElementValueContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        if (ctx.elementValueList() != null) {
            codeAux.append((String) visitElementValueList(ctx.elementValueList()));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitElementValueList(Java8Parser.ElementValueListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitElementValue(ctx.elementValue(0)));
        for(int i = 1; i<ctx.elementValue().size();i++){
            codeAux.append(", "+((String) visitElementValue(ctx.elementValue(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx) { return (T) ("@ "+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx) {
        return (T) ("@ "+((String) visitTypeName(ctx.typeName()))+"( "+((String) visitElementValue(ctx.elementValue()))+") ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArrayInitializer(Java8Parser.ArrayInitializerContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        if(ctx.variableInitializerList()!=null){
            codeAux.append((String) visitVariableInitializerList(ctx.variableInitializerList()));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitVariableInitializerList(Java8Parser.VariableInitializerListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitVariableInitializer(ctx.variableInitializer(0)));
        for(int i = 1; i<ctx.variableInitializer().size();i++){
            codeAux.append(", "+((String) visitVariableInitializer(ctx.variableInitializer(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitBlock(Java8Parser.BlockContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        if(ctx.blockStatements()!=null){
            codeAux.append((String) visitBlockStatements(ctx.blockStatements()));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitBlockStatements(Java8Parser.BlockStatementsContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.BlockStatementContext x : ctx.blockStatement()){
            codeAux.append((String) visitBlockStatement(x));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitBlockStatement(Java8Parser.BlockStatementContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) { return (T) (((String) visitChildren(ctx))+"; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.VariableModifierContext x: ctx.variableModifier()){
            codeAux.append((String) visitVariableModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorList(ctx.variableDeclaratorList())));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStatement(Java8Parser.StatementContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStatementNoShortIf(Java8Parser.StatementNoShortIfContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStatementWithoutTrailingSubstatement(Java8Parser.StatementWithoutTrailingSubstatementContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEmptyStatement_(Java8Parser.EmptyStatement_Context ctx) { return (T) ("; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLabeledStatement(Java8Parser.LabeledStatementContext ctx) { return (T) (ctx.Identifier().getText()+" : "+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLabeledStatementNoShortIf(Java8Parser.LabeledStatementNoShortIfContext ctx) { return (T) (ctx.Identifier().getText()+" : "+((String) visitChildren(ctx))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExpressionStatement(Java8Parser.ExpressionStatementContext ctx) { return (T) (((String) visitStatementExpression(ctx.statementExpression()))+"; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStatementExpression(Java8Parser.StatementExpressionContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitIfThenStatement(Java8Parser.IfThenStatementContext ctx){
        return (T) ("if ( "+((String) visitExpression(ctx.expression()))+") \n"+visitStatement(ctx.statement()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
        return (T) ("if ( "+((String) visitExpression(ctx.expression()))+") \n"+visitStatementNoShortIf(ctx.statementNoShortIf())+"\n else \n"+((String) visitStatement(ctx.statement())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitIfThenElseStatementNoShortIf(Java8Parser.IfThenElseStatementNoShortIfContext ctx) {
        return (T) ("if ( "+((String) visitExpression(ctx.expression()))+") \n"+visitStatementNoShortIf(ctx.statementNoShortIf(0))+"\n else \n"+((String) visitStatementNoShortIf(ctx.statementNoShortIf(1))));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAssertStatement(Java8Parser.AssertStatementContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("assert "+((String) visitExpression(ctx.expression(0))));
        if(ctx.expression().size()>1){
            codeAux.append(": "+((String) visitExpression(ctx.expression(0)))+"; \n");
        }else{
            codeAux.append("; \n");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSwitchStatement(Java8Parser.SwitchStatementContext ctx) {

        String currentCode =("switch ( "+((String) visitExpression(ctx.expression()))+") \n"+visitSwitchBlock(ctx.switchBlock()));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(currentCode.toString(),"match");
        }
        return (T) currentCode;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSwitchBlock(Java8Parser.SwitchBlockContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("{ \n");
        for(Java8Parser.SwitchBlockStatementGroupContext x : ctx.switchBlockStatementGroup()){
            codeAux.append((String) visitSwitchBlockStatementGroup(x));
        }
        for(Java8Parser.SwitchLabelContext x : ctx.switchLabel()){
            codeAux.append((String) visitSwitchLabel(x));
        }
        codeAux.append("} \n");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx) {
        return (T) (((String) visitSwitchLabels(ctx.switchLabels()))+((String) visitBlockStatements(ctx.blockStatements())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSwitchLabels(Java8Parser.SwitchLabelsContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitSwitchLabel(ctx.switchLabel(0)));
        for(int i =1; i<ctx.switchLabel().size();i++){
            codeAux.append((String) visitSwitchLabel(ctx.switchLabel(i)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
        if(ctx.constantExpression()!=null){
            return (T) ("case "+visitConstantExpression(ctx.constantExpression())+": \n");
        }else if(ctx.enumConstantName()!=null){
            return (T) ("case "+visitEnumConstantName(ctx.enumConstantName())+": \n");
        }
        return (T) ("default : \n");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnumConstantName(Java8Parser.EnumConstantNameContext ctx) { return (T) (ctx.Identifier().getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitWhileStatement(Java8Parser.WhileStatementContext ctx) {

        String currentCode =("while ( "+((String) visitExpression(ctx.expression()))+") \n"+((String) visitStatement(ctx.statement())));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(currentCode.toString(), "while");
        }
        return (T) currentCode;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitWhileStatementNoShortIf(Java8Parser.WhileStatementNoShortIfContext ctx) {
        String currentCode = ("while ( "+((String) visitExpression(ctx.expression()))+") \n"+((String) visitStatementNoShortIf(ctx.statementNoShortIf())));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(currentCode.toString(), "while");
        }
        return (T) currentCode;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitDoStatement(Java8Parser.DoStatementContext ctx) {
        String currentCode =("do "+((String) visitStatement(ctx.statement()))+"while ( "+((String) visitExpression(ctx.expression()))+") ; \n");
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(currentCode.toString(), "do_while");
        }
        return (T) currentCode;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitForStatement(Java8Parser.ForStatementContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitForStatementNoShortIf(Java8Parser.ForStatementNoShortIfContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("for ( ");
        logicScope.push("for");
        if(ctx.forInit()!=null){
            codeAux.append((String) visitForInit(ctx.forInit()));
        }
        codeAux.append("; ");
        if(ctx.expression()!=null){
            codeAux.append((String) visitExpression(ctx.expression()));
        }
        codeAux.append("; ");
        if(ctx.forUpdate()!=null){
            codeAux.append(visitForUpdate(ctx.forUpdate()));
        }
        codeAux.append(") \n"+((String) visitStatement(ctx.statement())));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(codeAux.toString(), "for");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitBasicForStatementNoShortIf(Java8Parser.BasicForStatementNoShortIfContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("for ( ");
        if(ctx.forInit()!=null){
            codeAux.append((String) visitForInit(ctx.forInit()));
        }
        codeAux.append("; ");
        if(ctx.expression()!=null){
            codeAux.append((String) visitExpression(ctx.expression()));
        }
        codeAux.append("; ");
        if(ctx.forUpdate()!=null){
            codeAux.append(visitForUpdate(ctx.forUpdate()));
        }
        codeAux.append(") \n"+((String) visitStatementNoShortIf(ctx.statementNoShortIf())));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(codeAux.toString(), "for");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitForInit(Java8Parser.ForInitContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitForUpdate(Java8Parser.ForUpdateContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitStatementExpressionList(Java8Parser.StatementExpressionListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitStatementExpression(ctx.statementExpression(0)));
        for(int i=1; i<ctx.statementExpression().size();i++){
            codeAux.append(", "+((String) visitStatementExpression(ctx.statementExpression(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("for ( ");
        for(Java8Parser.VariableModifierContext x:ctx.variableModifier()){
            codeAux.append((String) visitVariableModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorId(ctx.variableDeclaratorId()))+": "+visitExpression(ctx.expression())+") \n"+visitStatement(ctx.statement()));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(codeAux.toString(), "for");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEnhancedForStatementNoShortIf(Java8Parser.EnhancedForStatementNoShortIfContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("for ( ");
        for(Java8Parser.VariableModifierContext x:ctx.variableModifier()){
            codeAux.append((String) visitVariableModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorId(ctx.variableDeclaratorId()))+": "+visitExpression(ctx.expression())+") \n"+visitStatementNoShortIf(ctx.statementNoShortIf()));
        if(!scope.isEmpty()){
            scope.peek().getRawCode().put(codeAux.toString(), "for");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitBreakStatement(Java8Parser.BreakStatementContext ctx) { return (T) ("break "+ctx.Identifier().getText()+" ; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitContinueStatement(Java8Parser.ContinueStatementContext ctx) { return (T) ("continue "+ctx.Identifier().getText()+" ; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitReturnStatement(Java8Parser.ReturnStatementContext ctx) {
        if(ctx.expression()!=null){
            return (T) ("return "+((String) visitExpression(ctx.expression()))+"; \n");
        }
        return (T) ("return ; \n");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitThrowStatement(Java8Parser.ThrowStatementContext ctx) { return (T) ("throw "+((String) visitExpression(ctx.expression()))+"; \n"); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) { return (T) ("synchronized ( "+((String) visitExpression(ctx.expression()))+")"+((String) visitBlock(ctx.block()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTryStatement(Java8Parser.TryStatementContext ctx) {
        if (ctx.tryWithResourcesStatement() == null) {
            StringBuffer codeAux = new StringBuffer();
            codeAux.append("try "+((String) visitBlock(ctx.block())));
            if(ctx.catches()!=null){
                codeAux.append((String) visitCatches(ctx.catches()));
            }
            if(ctx.finally_()!=null){
                codeAux.append((String) visitFinally_(ctx.finally_()));
            }
            return (T) codeAux.toString();
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitCatches(Java8Parser.CatchesContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitCatchClause(ctx.catchClause(0)));
        for(int i = 1; i<ctx.catchClause().size();i++){
            codeAux.append((String) visitCatchClause(ctx.catchClause(i)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitCatchClause(Java8Parser.CatchClauseContext ctx) { return (T) ("catch ( "+((String) visitCatchFormalParameter(ctx.catchFormalParameter()))+") "+((String) visitBlock(ctx.block()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.VariableModifierContext x : ctx.variableModifier()){
            codeAux.append((String) visitVariableModifier(x));
        }
        codeAux.append(((String) visitCatchType(ctx.catchType()))+((String) visitVariableDeclaratorId(ctx.variableDeclaratorId())));
        return  (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitCatchType(Java8Parser.CatchTypeContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitUnannClassType(ctx.unannClassType()));
        for(Java8Parser.ClassTypeContext x: ctx.classType()){
            codeAux.append("| "+((String) visitClassType(x)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFinally_(Java8Parser.Finally_Context ctx) { return (T) ("finally "+visitBlock(ctx.block())); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTryWithResourcesStatement(Java8Parser.TryWithResourcesStatementContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("try "+((String) visitResourceSpecification(ctx.resourceSpecification()))+((String) visitBlock(ctx.block())));
        if(ctx.catches()!=null){
            codeAux.append((String) visitCatches(ctx.catches()));
        }
        if(ctx.finally_()!=null){
            codeAux.append((String) visitFinally_(ctx.finally_()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitResourceSpecification(Java8Parser.ResourceSpecificationContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("( "+((String) visitResourceList(ctx.resourceList())));
        if(ctx.getText().contains(";")){
            codeAux.append("; ");
        }
        codeAux.append(") ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitResourceList(Java8Parser.ResourceListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitResource(ctx.resource(0)));
        for(int i = 1; i<ctx.resource().size();i++){
            codeAux.append("; "+((String) visitResource(ctx.resource(i))));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitResource(Java8Parser.ResourceContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.VariableModifierContext x : ctx.variableModifier()){
            codeAux.append((String) visitVariableModifier(x));
        }
        codeAux.append(((String) visitUnannType(ctx.unannType()))+((String) visitVariableDeclaratorId(ctx.variableDeclaratorId()))+"= "+((String) visitExpression(ctx.expression())));
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimary(Java8Parser.PrimaryContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.arrayCreationExpression()==null){
            codeAux.append((String) visitPrimaryNoNewArray_lfno_primary(ctx.primaryNoNewArray_lfno_primary()));
        }else{
            codeAux.append((String) visitArrayCreationExpression(ctx.arrayCreationExpression()));
        }
        for(Java8Parser.PrimaryNoNewArray_lf_primaryContext x : ctx.primaryNoNewArray_lf_primary()){
            codeAux.append((String) visitPrimaryNoNewArray_lf_primary(x));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray(Java8Parser.PrimaryNoNewArrayContext ctx) {
        if(ctx.typeName()!=null){
            return (T) (ctx.getText()+" ");
        }else if(ctx.expression()!=null){
            return (T) ("( "+((String) visitExpression(ctx.expression()))+") ");
        }else if(ctx.getText().contains("this") || (ctx.getText().contains("void")&&ctx.getText().contains("class"))){
            return (T) (ctx.getText()+" ");
        }else{
            return visitChildren(ctx);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lf_arrayAccess(Java8Parser.PrimaryNoNewArray_lf_arrayAccessContext ctx) { return (T) " "; }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lfno_arrayAccess(Java8Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx) {
        if(ctx.typeName()!=null){
            return (T) (ctx.getText()+" ");
        }else if(ctx.expression()!=null){
            return (T) ("( "+((String) visitExpression(ctx.expression()))+") ");
        }else if(ctx.getText().contains("this") || (ctx.getText().contains("void")&&ctx.getText().contains("class"))){
            return (T) (ctx.getText()+" ");
        }else{
            return visitChildren(ctx);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primaryContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primaryContext ctx) { return (T) " "; }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primaryContext ctx) {
        if(ctx.typeName()!=null || ctx.unannPrimitiveType()!=null){
            return (T) (ctx.getText()+" ");
        }else if(ctx.expression()!=null){
            return (T) ("( "+((String) visitExpression(ctx.expression()))+") ");
        }else if(ctx.getText().contains("this") || (ctx.getText().contains("void")&&ctx.getText().contains("class"))){
            return (T) (ctx.getText()+" ");
        }else{
            return visitChildren(ctx);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primaryContext ctx) { return (T) " "; }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx) {
        if(ctx.typeName()!=null || ctx.unannPrimitiveType()!=null){
            return (T) (ctx.getText()+" ");
        }else if(ctx.expression()!=null){
            return (T) ("( "+((String) visitExpression(ctx.expression()))+") ");
        }else if(ctx.getText().contains("this") || (ctx.getText().contains("void")&&ctx.getText().contains("class"))){
            return (T) (ctx.getText()+" ");
        }else{
            return visitChildren(ctx);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassInstanceCreationExpression(Java8Parser.ClassInstanceCreationExpressionContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if (ctx.expressionName() != null && ctx.primary() != null) {
            if(ctx.expressionName()!=null){
                codeAux.append(((String) visitExpressionName(ctx.expressionName()))+".new ");
            }else if(ctx.primary()!=null){
                codeAux.append(((String) visitPrimary(ctx.primary()))+".new ");
            }
            if(ctx.typeArguments()!=null){
                codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
            }
            for(Java8Parser.AnnotationContext x : ctx.annotation()){
                codeAux.append((String) visitAnnotation(x));
            }
            codeAux.append(ctx.Identifier(0).getText()+" ");
            if(ctx.typeArgumentsOrDiamond()!=null){
                codeAux.append((String) visitTypeArgumentsOrDiamond(ctx.typeArgumentsOrDiamond()));
            }
            codeAux.append("( ");
            if(ctx.argumentList()!=null){
                codeAux.append((String) visitArgumentList(ctx.argumentList()));
            }
            codeAux.append(") ");
            if(ctx.classBody()!=null){
                codeAux.append((String) visitClassBody(ctx.classBody()));
            }
            return (T) codeAux.toString();
        }else{
            return (T) (ctx.getText()+" ");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassInstanceCreationExpression_lf_primary(Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(".new ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        for(Java8Parser.AnnotationContext x : ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        if(ctx.typeArgumentsOrDiamond()!=null){
            codeAux.append((String) visitTypeArgumentsOrDiamond(ctx.typeArgumentsOrDiamond()));
        }
        codeAux.append("( ");
        if(ctx.argumentList()!=null){
            codeAux.append((String) visitArgumentList(ctx.argumentList()));
        }
        codeAux.append(") ");
        if(ctx.classBody()!=null){
            codeAux.append((String) visitClassBody(ctx.classBody()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitClassInstanceCreationExpression_lfno_primary(Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx) {
        if(ctx.expressionName()==null){
            return (T) (ctx.getText()+" ");
        }else{
            StringBuffer codeAux = new StringBuffer();
            codeAux.append(((String) visitExpressionName(ctx.expressionName()))+".new ");
            if(ctx.typeArguments()!=null){
                codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
            }
            for(Java8Parser.AnnotationContext x : ctx.annotation()){
                codeAux.append((String) visitAnnotation(x));
            }
            codeAux.append(ctx.Identifier(0).getText()+" ");
            if(ctx.typeArgumentsOrDiamond()!=null){
                codeAux.append((String) visitTypeArgumentsOrDiamond(ctx.typeArgumentsOrDiamond()));
            }
            codeAux.append("( ");
            if(ctx.argumentList()!=null){
                codeAux.append((String) visitArgumentList(ctx.argumentList()));
            }
            codeAux.append(") ");
            if(ctx.classBody()!=null){
                codeAux.append((String) visitClassBody(ctx.classBody()));
            }
            return (T) codeAux.toString();
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitTypeArgumentsOrDiamond(Java8Parser.TypeArgumentsOrDiamondContext ctx) {
        if(ctx.typeArguments()==null){
            return (T) ("<> ");
        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFieldAccess(Java8Parser.FieldAccessContext ctx) {
        if(ctx.primary()!=null){
            return (T) (((String) visitPrimary(ctx.primary()))+"."+ctx.Identifier().getText()+" ");
        }else if(ctx.typeName()!=null){
            return (T) (((String) visitTypeName(ctx.typeName()))+".super."+ctx.Identifier().getText()+" ");
        }else{
            return (T) ("super."+ctx.Identifier().getText()+" ");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFieldAccess_lf_primary(Java8Parser.FieldAccess_lf_primaryContext ctx) { return (T) ("."+ctx.Identifier().getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitFieldAccess_lfno_primary(Java8Parser.FieldAccess_lfno_primaryContext ctx) {
        if(ctx.typeName()==null){
            return (T) ("super."+ctx.Identifier().getText()+" ");
        }else{
            return (T) (((String) visitTypeName(ctx.typeName()))+".super."+ctx.Identifier().getText()+" ");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArrayAccess(Java8Parser.ArrayAccessContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.expressionName()!=null){
            codeAux.append(((String) visitExpressionName(ctx.expressionName()))+"[ "+((String) visitExpression(ctx.expression(0)))+"] ");
        }else{
            codeAux.append(((String) visitPrimaryNoNewArray_lfno_arrayAccess(ctx.primaryNoNewArray_lfno_arrayAccess()))+"[ "+visitExpression(ctx.expression(0))+"] ");
        }
        for(int i = 0; i<ctx.primaryNoNewArray_lf_arrayAccess().size();i++){
            codeAux.append(((String) visitPrimaryNoNewArray_lf_arrayAccess(ctx.primaryNoNewArray_lf_arrayAccess(i)))+"[ "+visitExpression(ctx.expression(i+1))+"] ");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArrayAccess_lf_primary(Java8Parser.ArrayAccess_lf_primaryContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(((String) visitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(ctx.primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary()))+"[ "+visitExpression(ctx.expression(0))+"] ");
        for(int i = 0; i<ctx.primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary().size();i++){
            codeAux.append(((String) visitPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(ctx.primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(i)))+"[ "+visitExpression(ctx.expression(i+1))+"] ");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArrayAccess_lfno_primary(Java8Parser.ArrayAccess_lfno_primaryContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        if(ctx.expressionName()!=null){
            codeAux.append(((String) visitExpressionName(ctx.expressionName()))+"[ "+((String) visitExpression(ctx.expression(0)))+"] ");
        }else{
            codeAux.append(((String) visitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(ctx.primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary()))+"[ "+visitExpression(ctx.expression(0))+"] ");
        }
        for(int i = 0; i<ctx.primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary().size();i++){
            codeAux.append(((String) visitPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(ctx.primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(i)))+"[ "+visitExpression(ctx.expression(i+1))+"] ");
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodInvocation(Java8Parser.MethodInvocationContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodInvocation_lf_primary(Java8Parser.MethodInvocation_lf_primaryContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(".");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        codeAux.append(ctx.Identifier().toString()+"( ");
        if(ctx.argumentList()!=null){
            codeAux.append((String) visitArgumentList(ctx.argumentList()));
        }
        codeAux.append(") ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodInvocation_lfno_primary(Java8Parser.MethodInvocation_lfno_primaryContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArgumentList(Java8Parser.ArgumentListContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append((String) visitExpression(ctx.expression(0)));
        for(int i = 1;i<ctx.expression().size();i++){
            codeAux.append(", "+visitExpression(ctx.expression(i)));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodReference(Java8Parser.MethodReferenceContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodReference_lf_primary(Java8Parser.MethodReference_lf_primaryContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append(":: ");
        if(ctx.typeArguments()!=null){
            codeAux.append((String) visitTypeArguments(ctx.typeArguments()));
        }
        codeAux.append(ctx.Identifier().getText()+" ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMethodReference_lfno_primary(Java8Parser.MethodReference_lfno_primaryContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("new ");
        if(ctx.primitiveType()!=null){
            codeAux.append((String) visitPrimitiveType(ctx.primitiveType()));
        }else{
            codeAux.append((String) visitClassOrInterfaceType(ctx.classOrInterfaceType()));
        }
        if(ctx.dimExprs()!=null){
            codeAux.append((String) visitDimExprs(ctx.dimExprs()));
        }else{
            codeAux.append((String) visitDims(ctx.dims()));
        }
        if(ctx.arrayInitializer()!=null){
            codeAux.append((String) visitArrayInitializer(ctx.arrayInitializer()));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitDimExprs(Java8Parser.DimExprsContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.DimExprContext x: ctx.dimExpr()){
            codeAux.append((String) visitDimExpr(x));
        }
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitDimExpr(Java8Parser.DimExprContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        for(Java8Parser.AnnotationContext x: ctx.annotation()){
            codeAux.append((String) visitAnnotation(x));
        }
        codeAux.append("[ "+((String) visitExpression(ctx.expression()))+"] ");
        return (T) codeAux.toString();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConstantExpression(Java8Parser.ConstantExpressionContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExpression(Java8Parser.ExpressionContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLambdaExpression(Java8Parser.LambdaExpressionContext ctx) { return (T) (((String) visitLambdaParameters(ctx.lambdaParameters()))+" -> "+visitLambdaBody(ctx.lambdaBody())); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLambdaParameters(Java8Parser.LambdaParametersContext ctx) {
        if(ctx.Identifier()!=null){
            return (T) (ctx.Identifier().getText()+" ");
        }else if(ctx.inferredFormalParameterList()!=null){
            return (T) ("( "+((String) visitInferredFormalParameterList(ctx.inferredFormalParameterList()))+") ");
        }else{
            StringBuffer codeAux = new StringBuffer();
            codeAux.append("( ");
            if(ctx.formalParameterList()!=null){
                codeAux.append((String) visitFormalParameterList(ctx.formalParameterList()));
            }
            codeAux.append(") ");
            return (T) codeAux.toString();
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInferredFormalParameterList(Java8Parser.InferredFormalParameterListContext ctx) { return (T) (ctx.Identifier(0).getText()+" , "+ctx.Identifier(1).getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLambdaBody(Java8Parser.LambdaBodyContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAssignment(Java8Parser.AssignmentContext ctx) { return (T) (((String) visitLeftHandSide(ctx.leftHandSide()))+((String) visitAssignmentOperator(ctx.assignmentOperator()))+((String) visitExpression(ctx.expression()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitLeftHandSide(Java8Parser.LeftHandSideContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) { return (T) (ctx.getText()+" "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConditionalExpression(Java8Parser.ConditionalExpressionContext ctx) {
        if(ctx.expression()==null){
            return visitChildren(ctx);
        }else{
            return (T) (((String) visitConditionalOrExpression(ctx.conditionalOrExpression()))+"? "+((String) visitExpression(ctx.expression()))+": "+((String) visitConditionalExpression(ctx.conditionalExpression())));
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) {
        if(ctx.conditionalOrExpression()==null){
            return visitChildren(ctx);
        }
        return (T) ((String) visitConditionalOrExpression(ctx.conditionalOrExpression())+"|| "+((String) visitConditionalAndExpression(ctx.conditionalAndExpression())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) {
        if(ctx.conditionalAndExpression()==null){
            return visitChildren(ctx);
        }
        return (T) ((String) visitConditionalAndExpression(ctx.conditionalAndExpression())+"&& "+((String) visitInclusiveOrExpression(ctx.inclusiveOrExpression())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx) {
        if(ctx.inclusiveOrExpression()==null){
            return visitChildren(ctx);
        }
        return (T) ((String) visitInclusiveOrExpression(ctx.inclusiveOrExpression())+"| "+((String) visitExclusiveOrExpression(ctx.exclusiveOrExpression())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx) {
        if(ctx.exclusiveOrExpression()==null){
            return visitChildren(ctx);
        }
        return (T) ((String) visitExclusiveOrExpression(ctx.exclusiveOrExpression())+"^ "+((String) visitAndExpression(ctx.andExpression())));

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAndExpression(Java8Parser.AndExpressionContext ctx) {
        if(ctx.andExpression()==null){
            return visitChildren(ctx);
        }
        return (T) ((String) visitAndExpression(ctx.andExpression())+"& "+((String) visitEqualityExpression(ctx.equalityExpression())));

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitEqualityExpression(Java8Parser.EqualityExpressionContext ctx) {
        if(ctx.relationalExpression()!=null){
            return visitChildren(ctx);
        }
        if(ctx.getText().contains("==")){
            return (T) ((String) visitEqualityExpression(ctx.equalityExpression())+"== "+((String) visitRelationalExpression(ctx.relationalExpression())));
        }
        return (T) ((String) visitEqualityExpression(ctx.equalityExpression())+"!= "+((String) visitRelationalExpression(ctx.relationalExpression())));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitRelationalExpression(Java8Parser.RelationalExpressionContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitShiftExpression(Java8Parser.ShiftExpressionContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnaryExpression(Java8Parser.UnaryExpressionContext ctx) {
        if(ctx.unaryExpression()==null){
            return visitChildren(ctx);
        }
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) { return (T) ("++ "+((String) visitUnaryExpression(ctx.unaryExpression()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) { return (T) ("-- "+((String) visitUnaryExpression(ctx.unaryExpression()))); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) {
        if(ctx.unaryExpression()==null){
            return visitChildren(ctx);
        }
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPostfixExpression(Java8Parser.PostfixExpressionContext ctx) {
        visitChildren(ctx);
        return (T) (ctx.getText()+" ");
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) { return (T) (((String) visitPostfixExpression(ctx.postfixExpression()))+"++ "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPostIncrementExpression_lf_postfixExpression(Java8Parser.PostIncrementExpression_lf_postfixExpressionContext ctx) { return (T) "++ "; }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPostDecrementExpression(Java8Parser.PostDecrementExpressionContext ctx) { return (T) (((String) visitPostfixExpression(ctx.postfixExpression()))+"-- "); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitPostDecrementExpression_lf_postfixExpression(Java8Parser.PostDecrementExpression_lf_postfixExpressionContext ctx) { return (T) "-- " ;}
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public T visitCastExpression(Java8Parser.CastExpressionContext ctx) {
        StringBuffer codeAux = new StringBuffer();
        codeAux.append("( ");
        if(ctx.primitiveType()!=null){
            codeAux.append((String) visitPrimitiveType(ctx.primitiveType()));
        }else{
            codeAux.append((String) visitReferenceType(ctx.referenceType()));
            for(Java8Parser.AdditionalBoundContext x: ctx.additionalBound()){
                codeAux.append((String) visitAdditionalBound(x));
            }
        }
        codeAux.append(") ");
        if(ctx.unaryExpression()!=null){
            codeAux.append((String) visitUnaryExpression(ctx.unaryExpression()));
        }else if(ctx.unaryExpressionNotPlusMinus()!=null){
            codeAux.append((String) visitUnaryExpressionNotPlusMinus(ctx.unaryExpressionNotPlusMinus()));
        }else{
            codeAux.append((String) visitLambdaExpression(ctx.lambdaExpression()));
        }
        return (T) codeAux.toString();
    }

    private void getDuplicityInd(){
        for(int i = 0; i < functions.size(); i++){
            for(int j = i; j < functions.size(); j++){
                if(i==j){
                    for(String keys : functions.get(i).getRawCode().keySet()){
                        functions.get(i).getDuplicity().put(keys,new ArrayList<>());
                        if(functions.get(i).getRawCode().get(keys).equals("complete")){break;}
                        for(String keys2 : functions.get(i).getRawCode().keySet()){
                            if(!keys.equals(keys2) && functions.get(i).getRawCode().get(keys).equals(functions.get(i).getRawCode().get(keys2))){
                                double val = calculateJaccardSimilarity(keys,keys2);
                                functions.get(i).getDuplicity().get(keys).add(val);
                            }else{
                                functions.get(i).getDuplicity().get(keys).add(0.0);
                            }
                            //System.out.println(functions.get(i).getDuplicity().get(keys).toString());
                        }
                    }
                }else{
                    String val1 = "";
                    String val2 = "";
                    for(String keys : functions.get(i).getRawCode().keySet()){
                        if(functions.get(i).getRawCode().get(keys).equals("complete")){
                            val1 = keys;
                            break;
                        }
                    }
                    for(String keys : functions.get(j).getRawCode().keySet()){
                        if(functions.get(j).getRawCode().get(keys).equals("complete")){
                            val2 = keys;
                            break;
                        }
                    }
                    //System.out.println("Fun1: "+val1);
                    //System.out.println("Fun2: "+val2);
                    double val = calculateJaccardSimilarity(val1,val2);
                    System.out.println("Function "+functions.get(i).getName()+" and function "+functions.get(j).getName()+":");
                    System.out.println("\t"+val);
                }
            }
        }
    }

    private static double calculateJaccardSimilarity(String str1, String str2) {
        CharSequence charSeq1 = new StringBuilder(str1);
        CharSequence charSeq2 = new StringBuilder(str2);

        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.apply(charSeq1, charSeq2);
    }

    public ArrayList<FunctionSats> getFunc() {
        return functions;
    }

    public void setFunctions(ArrayList<FunctionSats> func) {
        this.functions = func;
    }

}