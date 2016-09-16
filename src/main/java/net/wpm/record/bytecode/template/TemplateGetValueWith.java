package net.wpm.record.bytecode.template;

import static java.util.Arrays.asList;
import static io.datakernel.codegen.Expressions.arg;
import static io.datakernel.codegen.Expressions.value;

import io.datakernel.codegen.AsmBuilder;
import net.wpm.record.blueprint.BlueprintMethod;
import net.wpm.record.blueprint.BlueprintVariable;

public class TemplateGetValueWith extends TemplateBase {

	protected BlueprintMethod blueprintMethod;
	
	public TemplateGetValueWith(BlueprintMethod blueprintMethod) {
		this.blueprintMethod = blueprintMethod;
	}

	@Override
	public void addBytecode(AsmBuilder<?> builder) {		
		BlueprintVariable variable = blueprintMethod.getVariable();
		builder.method(blueprintMethod.getName(), variable.getExternalType(), asList(variable.getExternalType()), readRecordWithExpression(variable, value(0), arg(0)));		
	}
	
}
